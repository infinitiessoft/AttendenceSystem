package service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import resources.specification.AttendRecordSpecification;
import resources.specification.CalendarEventSpecification;
import transfer.AttendRecordTransfer;
import transfer.AttendRecordTransfer.Status;
import calendar.CalendarEventDao;
import calendar.CalendarUtil;

import com.google.api.services.calendar.model.Event;

import dao.AttendRecordDao;
import dao.AttendRecordTypeDao;
import dao.EmployeeDao;
import dao.EventDao;
import entity.AttendRecord;
import entity.AttendRecordType;
import entity.Employee;
import exceptions.AttendRecordNotFoundException;
import exceptions.AttendRecordTypeNotFoundException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidEndDateException;
import exceptions.InvalidStartAndEndDateException;

public class AttendRecordServiceImpl implements AttendRecordService {

	private final static Logger logger = LoggerFactory
			.getLogger(AttendRecordServiceImpl.class);
	private final static String OFFICIAL_LEAVE_NAME = "official";
	private AttendRecordDao attendRecordDao;
	private AttendRecordTypeDao attendRecordTypeDao;
	private EmployeeDao employeeDao;
	private EventDao eventDao;
	private CalendarEventDao calendarEventDao;

	public AttendRecordServiceImpl(AttendRecordDao attendRecordDao,
			AttendRecordTypeDao attendRecordTypeDao, EmployeeDao employeeDao,
			EventDao eventDao, CalendarEventDao calendarEventDao) {
		this.attendRecordDao = attendRecordDao;
		this.attendRecordTypeDao = attendRecordTypeDao;
		this.employeeDao = employeeDao;
		this.eventDao = eventDao;
		this.calendarEventDao = calendarEventDao;
	}

	@Transactional
	@Override
	public AttendRecordTransfer retrieve(long id) {
		AttendRecord record = attendRecordDao.findOne(id);
		if (record == null) {
			throw new AttendRecordNotFoundException(id);
		}
		return toAttendRecordTransfer(record);
	}

	@Override
	public void delete(long id) {
		try {
			attendRecordDao.delete(id);
		} catch (NullPointerException e) {
			throw new AttendRecordNotFoundException(id);
		}
	}

	@Transactional
	@Override
	public AttendRecordTransfer save(AttendRecordTransfer attendRecord) {
		attendRecord.setId(null);
		AttendRecord newEntry = new AttendRecord();
		setUpAttendRecord(attendRecord, newEntry);
		newEntry.setBookDate(new Date());
		newEntry.setStatus(Status.pending.name());
		assetEndDateAfterStartDate(newEntry.getStartDate(),
				newEntry.getEndDate());
		assertIsBusinessDays(newEntry.getType(), newEntry.getStartDate(),
				newEntry.getEndDate());
		newEntry = attendRecordDao.save(newEntry);

		// if there is a manager the employee response to then send it an blank
		// event else it being permit automatically
		Employee employee = newEntry.getEmployee().getEmployee();
		if (employee != null) {
			entity.Event event = new entity.Event();
			event.setAttendRecord(newEntry);
			event.setEmployee(employee);
			eventDao.save(event);
		} else {
			newEntry.setStatus(Status.permit.name());
			newEntry = attendRecordDao.save(newEntry);
		}
		return toAttendRecordTransfer(newEntry);
	}

	private void assetEndDateAfterStartDate(Date startDate, Date endDate) {
		if (!endDate.after(startDate)) {
			throw new InvalidEndDateException();
		}
	}

	private void assertIsBusinessDays(AttendRecordType type, Date startDate,
			Date endDate) {
		if (OFFICIAL_LEAVE_NAME.equals(type.getName())) {
			return;
		}
		DateTime start = new DateTime(startDate);
		DateTime end = new DateTime(endDate);
		Interval interval = new Interval(start, end);

		List<Event> events = new ArrayList<Event>();

		CalendarEventSpecification startSpec = new CalendarEventSpecification();
		startSpec.setTimeMin(startDate);
		startSpec.setTimeMax(endDate);
		try {
			events.addAll(calendarEventDao.findAll(startSpec, null));
		} catch (IOException e) {
			throw new RuntimeException("calendar api error", e);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException("calendar api authentication error", e);
		}
		logger.debug("events size: {}", events.size());

		CalendarUtil.checkNotOverlaps(interval, events);
	}

	@Transactional
	@Override
	public AttendRecordTransfer update(long id, AttendRecordTransfer updated) {
		AttendRecord attendRecord = attendRecordDao.findOne(id);
		if (attendRecord == null) {
			throw new AttendRecordNotFoundException(id);
		}
		setUpAttendRecord(updated, attendRecord);
		attendRecord.setBookDate(new Date());
		assetEndDateAfterStartDate(attendRecord.getStartDate(),
				attendRecord.getEndDate());
		assertIsBusinessDays(attendRecord.getType(),
				attendRecord.getStartDate(), attendRecord.getEndDate());
		return toAttendRecordTransfer(attendRecordDao.save(attendRecord));
	}

	@Transactional
	@Override
	public Page<AttendRecordTransfer> findAll(AttendRecordSpecification spec,
			Pageable pageable) {
		List<AttendRecordTransfer> transfers = new ArrayList<AttendRecordTransfer>();
		Page<AttendRecord> attendRecords = attendRecordDao.findAll(spec,
				pageable);
		for (AttendRecord attendRecord : attendRecords) {
			transfers.add(toAttendRecordTransfer(attendRecord));
		}
		Page<AttendRecordTransfer> rets = new PageImpl<AttendRecordTransfer>(
				transfers, pageable, attendRecords.getTotalElements());
		return rets;
	}

	private void setUpAttendRecord(AttendRecordTransfer transfer,
			AttendRecord newEntry) {
		if (transfer.isStartDateSet()) {
			newEntry.setStartDate(transfer.getStartDate());
		}
		if (transfer.isEndDateSet()) {
			newEntry.setEndDate(transfer.getEndDate());
		}
		// ignore update bookdate
		// if (transfer.isBookDateSet()) {
		// newEntry.setBookDate(transfer.getBookDate());
		// }
		if (transfer.isReasonSet()) {
			newEntry.setReason(transfer.getReason());
		}
		if (newEntry.getStartDate() == null || newEntry.getEndDate() == null) {
			throw new InvalidStartAndEndDateException();
		}
		newEntry.setDuration(countDuration(newEntry.getStartDate(),
				newEntry.getEndDate()));
		if (transfer.isTypeSet()) {
			if (transfer.getType().isIdSet()) {
				entity.AttendRecordType type = attendRecordTypeDao
						.findOne(transfer.getType().getId());
				if (type == null) {
					throw new AttendRecordTypeNotFoundException(transfer
							.getType().getId());
				}
				newEntry.setType(type);
			}
		}
		if (transfer.isEmployeeSet()) {
			if (transfer.getApplicant().isIdSet()) {
				entity.Employee employee = employeeDao.findOne(transfer
						.getApplicant().getId());
				if (employee == null) {
					throw new EmployeeNotFoundException(transfer.getApplicant()
							.getId());
				}
				newEntry.setEmployee(employee);
			}
		}
	}

	private double countDuration(Date startDate, Date endDate) {
		return CalendarUtil.countDuration(startDate, endDate);
	}

	private AttendRecordTransfer toAttendRecordTransfer(
			AttendRecord attendRecord) {
		AttendRecordTransfer ret = new AttendRecordTransfer();
		ret.setId(attendRecord.getId());
		AttendRecordTransfer.Employee employee = new AttendRecordTransfer.Employee();
		employee.setId(attendRecord.getEmployee().getId());
		employee.setName(attendRecord.getEmployee().getName());
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(attendRecord.getType().getId());
		type.setName(attendRecord.getType().getName());
		ret.setType(type);
		ret.setBookDate(attendRecord.getBookDate());
		ret.setDuration(attendRecord.getDuration());
		ret.setApplicant(employee);
		ret.setReason(attendRecord.getReason());
		ret.setStartDate(attendRecord.getStartDate());
		ret.setEndDate(attendRecord.getEndDate());
		ret.setStatus(Status.valueOf(attendRecord.getStatus()));

		return ret;
	}

//	private void setUpStatus(AttendRecord attendRecord, AttendRecordTransfer ret) {
//		if (isFinish(attendRecord)) {
//			ret.setStatus(Status.permit);
//			for (entity.Event event : attendRecord.getEvents()) {
//				if (Status.reject.equals(event.getAction())) {
//					ret.setStatus(Status.reject);
//					return;
//				}
//			}
//		} else {
//			ret.setStatus(Status.pending);
//		}
//
//	}

//	private boolean isFinish(AttendRecord attendRecord) {
//		int permitNum = 0;
//
//		// if there is no one the employee have to response to then the
//		// leaves being permit automatically
//		if (attendRecord.getEmployee().getEmployee() == null) {
//			return true;
//		}
//
//		// if the leaves is less than 1 day then it have to get 1 permit.
//		if (attendRecord.getDuration() <= 1d) {
//			permitNum = 1;
//		}
//
//		// if the leaves is more than 2 days and there is someone the employee's
//		// manager have to response to then it have to get 2 permit.
//		if (attendRecord.getEmployee().getEmployee().getEmployee() == null) {
//			permitNum = 1;
//		} else {
//			permitNum = 2;
//		}
//
//		for (entity.Event event : attendRecord.getEvents()) {
//			if (Status.reject.equals(event.getAction())) {
//				return true;
//			}
//			if (Status.permit.equals(event.getAction())) {
//				permitNum--;
//			}
//			if (permitNum == 0) {
//				return true;
//			}
//		}
//
//		return false;
//	}
}