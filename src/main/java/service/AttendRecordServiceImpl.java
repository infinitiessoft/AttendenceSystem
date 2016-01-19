package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import resources.specification.AttendRecordSpecification;
import resources.specification.CalendarEventSpecification;
import transfer.AttendRecordReport;
import transfer.AttendRecordTransfer;
import transfer.AttendRecordTransfer.Status;
import transfer.EventTransfer;
import calendar.CalendarEventService;
import calendar.CalendarUtil;

import com.google.api.services.calendar.model.Event;

import dao.AttendRecordDao;
import dao.AttendRecordTypeDao;
import dao.EmployeeDao;
import dao.EmployeeLeaveDao;
import dao.LeavesettingDao;
import entity.AttendRecord;
import entity.AttendRecordType;
import entity.Employee;
import entity.EmployeeLeave;
import entity.Leavesetting;
import exceptions.AttendRecordNotFoundException;
import exceptions.AttendRecordTypeNotFoundException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidEndDateException;
import exceptions.InvalidStartAndEndDateException;
import exceptions.LeavesettingNotFoundException;
import exceptions.NoEnoughLeaveDaysException;

public class AttendRecordServiceImpl implements AttendRecordService {

	private final static Logger logger = LoggerFactory
			.getLogger(AttendRecordServiceImpl.class);
	private final static String OFFICIAL_LEAVE_NAME = "official";
	private AttendRecordDao attendRecordDao;
	private AttendRecordTypeDao attendRecordTypeDao;
	private EmployeeDao employeeDao;
	private EventService eventService;
	private CalendarEventService calendarEventDao;
	private LeavesettingDao leavesettingDao;
	private EmployeeLeaveDao employeeLeaveDao;

	public AttendRecordServiceImpl(AttendRecordDao attendRecordDao,
			AttendRecordTypeDao attendRecordTypeDao, EmployeeDao employeeDao,
			EventService eventService, CalendarEventService calendarEventDao,
			LeavesettingDao leavesettingDao, EmployeeLeaveDao employeeLeaveDao) {
		this.attendRecordDao = attendRecordDao;
		this.attendRecordTypeDao = attendRecordTypeDao;
		this.employeeDao = employeeDao;
		this.eventService = eventService;
		this.calendarEventDao = calendarEventDao;
		this.leavesettingDao = leavesettingDao;
		this.employeeLeaveDao = employeeLeaveDao;
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
		Employee applicant = employeeDao.findOne(attendRecord.getApplicant()
				.getId());
		assertStartDateAfterJoinDate(newEntry.getStartDate(),
				applicant.getDateofjoined());
		assetEndDateAfterStartDate(newEntry.getStartDate(),
				newEntry.getEndDate());
		assertStartDateNotOver30DaysFromNow(newEntry.getStartDate());
		assertIsBusinessDays(newEntry.getType(), newEntry.getStartDate(),
				newEntry.getEndDate());
		assertEnoughAvailableLeaveDays(attendRecord);
		newEntry = attendRecordDao.save(newEntry);

		// if there is a manager the employee response to then send it an
		// blank
		// event else it being permit automatically
		Employee approver = newEntry.getEmployee().getEmployee();
		if (approver != null) {
			EventTransfer event = new EventTransfer();
			EventTransfer.AttendRecord record = new EventTransfer.AttendRecord();
			record.setId(newEntry.getId());
			EventTransfer.Employee employee = new EventTransfer.Employee();
			employee.setId(approver.getId());
			event.setApprover(employee);
			event.setRecord(record);
			eventService.save(event);
		} else {
			newEntry.setStatus(Status.permit.name());
			newEntry = attendRecordDao.save(newEntry);
			Event event = CalendarUtil.toEvent(newEntry);
			calendarEventDao.save(event);
		}
		return toAttendRecordTransfer(newEntry);
	}

	private void assertStartDateAfterJoinDate(Date startDate, Date dateofjoined) {
		if (!startDate.after(dateofjoined)) {
			throw new InvalidStartAndEndDateException(
					"startDate should always over your joined date");
		}
	}

	private void assertStartDateNotOver30DaysFromNow(Date startDate) {
		Calendar startC = Calendar.getInstance();
		startC.setTime(startDate);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_YEAR, 30);
		if (startC.after(now)) {
			throw new InvalidStartAndEndDateException(
					"startDate cannot over 30 days from now");
		}
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
		events.addAll(calendarEventDao.findAll(startSpec, null));
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
		Employee applicant = attendRecord.getEmployee();
		assertStartDateAfterJoinDate(attendRecord.getStartDate(),
				applicant.getDateofjoined());
		assetEndDateAfterStartDate(attendRecord.getStartDate(),
				attendRecord.getEndDate());
		assertStartDateNotOver30DaysFromNow(attendRecord.getStartDate());
		assertIsBusinessDays(attendRecord.getType(),
				attendRecord.getStartDate(), attendRecord.getEndDate());
		return toAttendRecordTransfer(attendRecordDao.save(attendRecord));
	}

	private void assertEnoughAvailableLeaveDays(
			AttendRecordTransfer attendRecord) {
		Employee employee = employeeDao.findOne(attendRecord.getApplicant()
				.getId());
		Date startDate = attendRecord.getStartDate();
		Date endDate = attendRecord.getEndDate();
		Date joinDate = employee.getDateofjoined();

		double duration = countDuration(startDate, endDate);
		Calendar startC = Calendar.getInstance();
		startC.setTime(startDate);
		Calendar endC = Calendar.getInstance();
		endC.setTime(endDate);
		Calendar joinC = Calendar.getInstance();
		joinC.setTime(joinDate);
		Calendar joinY = Calendar.getInstance();
		joinY.setTime(joinDate);
		joinY.set(Calendar.YEAR, startC.get(Calendar.YEAR));
		Date joinDay = joinY.getTime();

		AttendRecordType type = attendRecordTypeDao.findOne(attendRecord
				.getType().getId());

		if (!CalendarUtil.overDateOfJoined(startDate, endDate, joinDate)) {
			logger.debug("Time Range is not over dateOfJoined");
			long years = getYearOfJoined(joinDate, startDate);
			logger.debug("Employee joined years : {}", years);
			Leavesetting leavesetting = leavesettingDao.findByTypeIdAndYear(
					attendRecord.getType().getId(), years);
			if (leavesetting == null) {
				throw new LeavesettingNotFoundException(type.getName(), years);
			}
			EmployeeLeave employeeLeave = findEmployeeLeave(employee,
					leavesetting);

			// there are enough unused leave days
			if (leavesetting.getDays() < 0d
					|| (leavesetting.getDays() - employeeLeave.getUsedDays()) >= duration) {
				employeeLeave
						.setUsedDays((employeeLeave.getUsedDays() + duration));
				employeeLeaveDao.save(employeeLeave);
				return;
			} else {
				throw new NoEnoughLeaveDaysException(type.getName(), duration,
						employeeLeave.getUsedDays(), leavesetting.getDays(),
						leavesetting.getYear());
			}
		} else {
			if (startC.get(Calendar.DAY_OF_MONTH) == joinC
					.get(Calendar.DAY_OF_MONTH)) {
				logger.debug("Start date is joined date");
				long years = getYearOfJoined(joinDate, startDate);
				logger.debug("Employee joined years : {}", years);
				Leavesetting leavesetting = leavesettingDao
						.findByTypeIdAndYear(attendRecord.getType().getId(),
								years);
				if (leavesetting == null) {
					throw new LeavesettingNotFoundException(type.getName(),
							years);
				}
				EmployeeLeave employeeLeave = findEmployeeLeave(employee,
						leavesetting);
				if (leavesetting.getDays() < 0d
						|| (leavesetting.getDays() - employeeLeave
								.getUsedDays()) >= duration) {
					employeeLeave
							.setUsedDays((employeeLeave.getUsedDays() + duration));
					employeeLeaveDao.save(employeeLeave);
					return;
				} else {
					throw new NoEnoughLeaveDaysException(type.getName(),
							duration, employeeLeave.getUsedDays(),
							leavesetting.getDays(), leavesetting.getYear());
				}
			} else if (endC.get(Calendar.DAY_OF_MONTH) == joinC
					.get(Calendar.DAY_OF_MONTH)) {
				logger.debug("End date is joined date");
				long years = getYearOfJoined(joinDate, startDate);
				logger.debug("Employee joined years : {}", years);
				Leavesetting past = leavesettingDao.findByTypeIdAndYear(
						attendRecord.getType().getId(), years);
				if (past == null) {
					throw new LeavesettingNotFoundException(type.getName(),
							years);
				}
				EmployeeLeave employeeLeave = findEmployeeLeave(employee, past);
				double newDuartion = countDuration(joinDay, endDate);
				logger.debug("Next Year Duration : {}", newDuartion);
				if (past.getDays() < 0d
						|| (past.getDays() - employeeLeave.getUsedDays()) >= (duration - newDuartion)) {
					employeeLeave
							.setUsedDays((employeeLeave.getUsedDays() + (duration - newDuartion)));
					employeeLeaveDao.save(employeeLeave);
					Leavesetting newLeavesetting = leavesettingDao
							.findByTypeIdAndYear(
									attendRecord.getType().getId(), (years + 1));
					if (newLeavesetting == null) {
						throw new LeavesettingNotFoundException(type.getName(),
								years + 1);
					}
					employeeLeave = findEmployeeLeave(employee, newLeavesetting);
					if (newLeavesetting.getDays() < 0d
							|| (newLeavesetting.getDays() - employeeLeave
									.getUsedDays()) >= newDuartion) {
						employeeLeave
								.setUsedDays((employeeLeave.getUsedDays() + newDuartion));
						employeeLeaveDao.save(employeeLeave);
						return;
					} else {
						throw new NoEnoughLeaveDaysException(type.getName(),
								newDuartion, employeeLeave.getUsedDays(),
								newLeavesetting.getDays(),
								newLeavesetting.getYear());
					}
				} else {
					throw new NoEnoughLeaveDaysException(type.getName(),
							(duration - newDuartion),
							employeeLeave.getUsedDays(), past.getDays(),
							past.getYear());
				}
			} else {
				Calendar calE = Calendar.getInstance();
				calE.setTime(joinDay);
				calE.add(Calendar.DATE, -1);
				calE.set(Calendar.AM_PM, 1);
				calE.set(Calendar.HOUR, 6);
				double pastDuration = countDuration(startDate, calE.getTime());

				long years = getYearOfJoined(joinDate, startDate);
				Leavesetting past = leavesettingDao.findByTypeIdAndYear(
						attendRecord.getType().getId(), years);
				if (past == null) {
					throw new LeavesettingNotFoundException(type.getName(),
							years);
				}
				EmployeeLeave employeeLeave = findEmployeeLeave(employee, past);
				if (past.getDays() < 0d
						|| (past.getDays() - employeeLeave.getUsedDays()) >= pastDuration) {
					employeeLeave
							.setUsedDays((employeeLeave.getUsedDays() + pastDuration));
					employeeLeaveDao.save(employeeLeave);
					double newDuration = countDuration(joinDay, endDate);
					Leavesetting newLeavesetting = leavesettingDao
							.findByTypeIdAndYear(
									attendRecord.getType().getId(), (years + 1));
					if (newLeavesetting == null) {
						throw new LeavesettingNotFoundException(type.getName(),
								years + 1);
					}
					employeeLeave = findEmployeeLeave(employee, newLeavesetting);
					if (newLeavesetting.getDays() < 0d
							|| (newLeavesetting.getDays() - employeeLeave
									.getUsedDays()) >= newDuration) {
						employeeLeave
								.setUsedDays((employeeLeave.getUsedDays() + newDuration));
						employeeLeaveDao.save(employeeLeave);
						return;
					} else {
						throw new NoEnoughLeaveDaysException(type.getName(),
								newDuration, employeeLeave.getUsedDays(),
								newLeavesetting.getDays(),
								newLeavesetting.getYear());
					}
				} else {
					throw new NoEnoughLeaveDaysException(type.getName(),
							pastDuration, employeeLeave.getUsedDays(),
							past.getDays(), past.getYear());
				}
			}
		}
	}

	private EmployeeLeave findEmployeeLeave(Employee employee,
			Leavesetting leavesetting) {
		EmployeeLeave employeeLeave = employeeLeaveDao
				.findByEmployeeIdAndLeavesettingId(employee.getId(),
						leavesetting.getId());
		if (employeeLeave == null) {
			employeeLeave = new EmployeeLeave();
			employeeLeave.setEmployee(employee);
			employeeLeave.setLeavesetting(leavesetting);
			employeeLeave.setUsedDays(0d);
			employeeLeave = employeeLeaveDao.save(employeeLeave);
		}
		return employeeLeave;
	}

	private long getYearOfJoined(Date joinedDate, Date startDate) {
		DateTime joined = new DateTime(joinedDate);
		DateTime start = new DateTime(startDate);
		Period period = new Period(joined, start);
		return period.getYears() + 1;
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

	@Transactional
	@Override
	public List<AttendRecordReport> findAll(AttendRecordSpecification spec) {

		List<AttendRecordReport> reports = new ArrayList<AttendRecordReport>();
		List<AttendRecord> attendRecords = attendRecordDao.findAll(spec);
		for (AttendRecord attendRecord : attendRecords) {
			reports.add(toAttendRecordReport(attendRecord));
		}
		return reports;
	}

	private AttendRecordReport toAttendRecordReport(AttendRecord attendRecord) {
		AttendRecordReport ret = new AttendRecordReport();
		ret.setId(attendRecord.getId());
		ret.setType(attendRecord.getType().getName());
		ret.setBookDate(attendRecord.getBookDate());
		ret.setDuration(attendRecord.getDuration());
		ret.setApplicant(attendRecord.getEmployee().getName());
		ret.setReason(attendRecord.getReason());
		ret.setStartDate(attendRecord.getStartDate());
		ret.setEndDate(attendRecord.getEndDate());
		ret.setStatus(Status.valueOf(attendRecord.getStatus()));

		return ret;

	}
}