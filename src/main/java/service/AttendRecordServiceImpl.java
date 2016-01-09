package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import resources.specification.AttendRecordSpecification;
import resources.specification.CalendarEventSpecification;
import transfer.AttendRecordTransfer;
import calendar.CalendarEventDao;
import calendar.CalendarUtil;

import com.google.api.services.calendar.model.Event;

import dao.AttendRecordDao;
import dao.AttendRecordTypeDao;
import dao.EmployeeDao;
import dao.EventDao;
import entity.AttendRecord;
import entity.AttendRecordType;
import exceptions.AttendRecordNotFoundException;
import exceptions.AttendRecordTypeNotFoundException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidEndDateException;
import exceptions.InvalidStartAndEndDateException;

public class AttendRecordServiceImpl implements AttendRecordService {

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
		AttendRecord dep = new AttendRecord();
		setUpAttendRecord(attendRecord, dep);
		dep.setBookDate(new Date());
		assetEndDateAfterStartDate(dep.getStartDate(), dep.getEndDate());
		assertIsBusinessDays(dep.getType(), dep.getStartDate(),
				dep.getEndDate());
		dep = attendRecordDao.save(dep);
		entity.Event event = new entity.Event();
		return toAttendRecordTransfer(dep);
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
		}

		CalendarUtil.checkNotOverlaps(interval, events);
	}

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
			if (transfer.getEmployee().isIdSet()) {
				entity.Employee employee = employeeDao.findOne(transfer
						.getEmployee().getId());
				if (employee == null) {
					throw new EmployeeNotFoundException(transfer.getEmployee()
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
		ret.setEmployee(employee);
		ret.setReason(attendRecord.getReason());
		ret.setStartDate(attendRecord.getStartDate());
		ret.setEndDate(attendRecord.getEndDate());

		return ret;

	}

}