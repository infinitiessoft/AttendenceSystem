package service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import resources.specification.AttendRecordSpecification;
import resources.specification.CalendarEventSpecification;
import service.AttendRecordService;
import service.CalendarEventService;
import service.MailService;
import transfer.AttendRecordReport;
import transfer.AttendRecordTransfer;
import transfer.AttendRecordTransfer.Status;
import transfer.EventTransfer.Action;
import transfer.Metadata;
import transfer.PermittedAttendRecord;
import util.CalendarUtils;
import util.MailWritter;

import com.google.api.services.calendar.model.Event;
import com.google.common.base.Preconditions;

import dao.AttendRecordDao;
import dao.AttendRecordTypeDao;
import dao.EmployeeDao;
import dao.EmployeeLeaveDao;
import dao.EventDao;
import dao.LeavesettingDao;
import entity.AttendRecord;
import entity.AttendRecordType;
import entity.Employee;
import entity.EmployeeLeave;
import entity.Leavesetting;
import exceptions.AttendRecordNotFoundException;
import exceptions.AttendRecordTypeNotFoundException;
import exceptions.BadRequestException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidEndDateException;
import exceptions.InvalidStartAndEndDateException;
import exceptions.LeavesettingNotFoundException;
import exceptions.ModificationNotAllowException;
import exceptions.NoEnoughLeaveDaysException;
import exceptions.RemovingIntegrityViolationException;

public class AttendRecordServiceImpl implements AttendRecordService {

	private final static Logger logger = LoggerFactory
			.getLogger(AttendRecordServiceImpl.class);
	private final static String OFFICIAL_LEAVE_NAME = "official";
	private AttendRecordDao attendRecordDao;
	private AttendRecordTypeDao attendRecordTypeDao;
	private EmployeeDao employeeDao;
	private EventDao eventDao;
	private MailService mailService;
	private MailWritter writter;
	private CalendarEventService calendarEventService;
	private LeavesettingDao leavesettingDao;
	private EmployeeLeaveDao employeeLeaveDao;

	public AttendRecordServiceImpl(AttendRecordDao attendRecordDao,
			AttendRecordTypeDao attendRecordTypeDao, EmployeeDao employeeDao,
			EventDao eventDao, MailService mailService, MailWritter writter,
			CalendarEventService calendarEventService,
			LeavesettingDao leavesettingDao, EmployeeLeaveDao employeeLeaveDao) {
		this.attendRecordDao = attendRecordDao;
		this.attendRecordTypeDao = attendRecordTypeDao;
		this.employeeDao = employeeDao;
		this.eventDao = eventDao;
		this.mailService = mailService;
		this.calendarEventService = calendarEventService;
		this.leavesettingDao = leavesettingDao;
		this.employeeLeaveDao = employeeLeaveDao;
		this.writter = writter;
	}

	// @Override
	// public long count(Specification<AttendRecord> spec) {
	// long count = attendRecordDao.count(spec);
	// return count;
	// }

	@Transactional
	@Override
	public AttendRecordTransfer retrieve(long id) {
		AttendRecord record = attendRecordDao.findOne(id);
		if (record == null) {
			throw new AttendRecordNotFoundException(id);
		}
		return AttendRecordTransfer.toAttendRecordTransfer(record);
	}

	@Transactional
	@Override
	public AttendRecordTransfer retrieve(Specification<AttendRecord> spec) {
		AttendRecord record = attendRecordDao.findOne(spec);
		if (record == null) {
			throw new AttendRecordNotFoundException();
		}
		return AttendRecordTransfer.toAttendRecordTransfer(record);
	}

	@Transactional
	@Override
	public void delete(long id, boolean force) {
		AttendRecord record = attendRecordDao.findOne(id);
		if (record == null) {
			throw new AttendRecordNotFoundException(id);
		}
		if (!force
				&& (!Status.pending.name().equals(record.getStatus()) || record
						.getEvents().size() > 1)) {
			throw new ModificationNotAllowException(id);
		}
		recoverEmployeeLeave(record);
		try {
			attendRecordDao.delete(record);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new RemovingIntegrityViolationException(AttendRecord.class);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			throw new AttendRecordNotFoundException(id);
		}
	}

	@Transactional
	@Override
	public AttendRecordTransfer save(AttendRecordTransfer attendRecord) {
		Preconditions.checkNotNull(attendRecord.getApplicant());
		Preconditions.checkNotNull(attendRecord.getApplicant().getId());

		attendRecord.setId(null);
		AttendRecord newEntry = new AttendRecord();
		setUpAttendRecord(attendRecord, newEntry);
		newEntry.setBookDate(new Date());
		newEntry.setStatus(Status.pending.name());
		Employee applicant = newEntry.getEmployee();
		logger.debug("applicant:{}", applicant);
		assertStartDateAfterJoinDate(newEntry.getStartDate(),
				applicant.getDateofjoined());
		assetEndDateAfterStartDate(newEntry.getStartDate(),
				newEntry.getEndDate());
		// assertStartDateNotOver30DaysFromNow(newEntry.getStartDate());
		assertIsBusinessDays(newEntry.getType(), newEntry.getStartDate(),
				newEntry.getEndDate());
		assertEnoughAvailableLeaveDays(newEntry);

		// if there is a manager the employee response to then send it an
		// blank
		// event else it being permit automatically
		Employee approver = applicant.getEmployee();
		if (approver != null) {
			logger.debug("approver exist:{}", approver);
			newEntry = attendRecordDao.save(newEntry);
			entity.Event event = new entity.Event();

			event.setEmployee(approver);
			event.setAttendRecord(newEntry);
			event.setAction(Action.pending.name());
			event = eventDao.save(event);
			try {
				String subject = writter.buildSubject(newEntry);
				String body = writter.buildBody(newEntry);
				mailService.sendMail(approver.getEmail(), subject, body);
			} catch (Throwable t) {
				logger.warn("send mail failed, ignore", t);
			}

		} else {
			logger.debug("approver not exist");
			newEntry.setStatus(Status.permit.name());
			newEntry = attendRecordDao.save(newEntry);
			Event event = CalendarUtils.toEvent(newEntry);
			calendarEventService.save(event);
		}
		return AttendRecordTransfer.toAttendRecordTransfer(newEntry);
	}

	private void assertStartDateAfterJoinDate(Date startDate, Date dateofjoined) {
		if (!startDate.after(dateofjoined)) {
			throw new InvalidStartAndEndDateException(
					"startDate should always over your joined date");
		}
	}

	// private void assertStartDateNotOver30DaysFromNow(Date startDate) {
	// Calendar startC = Calendar.getInstance();
	// startC.setTime(startDate);
	// Calendar now = Calendar.getInstance();
	// now.add(Calendar.DAY_OF_YEAR, 30);
	// if (startC.after(now)) {
	// throw new InvalidStartAndEndDateException(
	// "startDate cannot over 30 days from now");
	// }
	// }

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
		events.addAll(calendarEventService.findAll(startSpec, null));
		logger.debug("events size: {}", events.size());

		CalendarUtils.checkNotOverlaps(interval, events);
	}

	@Transactional
	@Override
	public AttendRecordTransfer update(long id, AttendRecordTransfer updated,
			boolean force) {
		AttendRecord attendRecord = attendRecordDao.findOne(id);
		return update(attendRecord, updated, force);
	}

	private void assertEnoughAvailableLeaveDays(AttendRecord attendRecord) {
		Employee employee = attendRecord.getEmployee();
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

		AttendRecordType type = attendRecord.getType();

		if (!CalendarUtils.overDateOfJoined(startDate, endDate, joinDate)) {
			logger.debug("Time Range is not over dateOfJoined");
			long years = CalendarUtils.getYearOfJoined(joinDate, startDate);
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
				long years = CalendarUtils.getYearOfJoined(joinDate, startDate);
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
				long years = CalendarUtils.getYearOfJoined(joinDate, startDate);
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
				calE.set(Calendar.HOUR_OF_DAY, 18);
				double pastDuration = countDuration(startDate, calE.getTime());
				long years = CalendarUtils.getYearOfJoined(joinDate, startDate);
				logger.debug(
						"year:{}, pastDuration:{}, startDate:{}, endDate:{}",
						new Object[] { years, pastDuration, startDate,
								calE.getTime() });
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

	@Transactional
	@Override
	public Page<AttendRecordTransfer> findAll(Specification<AttendRecord> spec,
			Pageable pageable) {
		List<AttendRecordTransfer> transfers = new ArrayList<AttendRecordTransfer>();
		Page<AttendRecord> attendRecords = attendRecordDao.findAll(spec,
				pageable);
		for (AttendRecord attendRecord : attendRecords) {
			transfers.add(AttendRecordTransfer
					.toAttendRecordTransfer(attendRecord));
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
		if (transfer.isTypeSet() && transfer.getType() != null) {
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
		if (transfer.isEmployeeSet() && transfer.getApplicant() != null) {
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
		if (newEntry.getEmployee() == null) {
			throw new BadRequestException("Invalid applicant");
		}

		if (newEntry.getType() == null) {
			throw new BadRequestException("Invalid type");
		}
	}

	private double countDuration(Date startDate, Date endDate) {
		return CalendarUtils.countDuration(startDate, endDate);
	}

	@Transactional
	@Override
	public List<AttendRecordReport> findAll(Specification<AttendRecord> spec) {

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

	@Override
	public AttendRecordTransfer reject(long id) {
		AttendRecord record = attendRecordDao.findOne(id);
		if (record == null) {
			throw new AttendRecordNotFoundException(id);
		}
		record.setStatus(AttendRecordTransfer.Status.reject.name());
		recoverEmployeeLeave(record);
		record = attendRecordDao.save(record);
		return AttendRecordTransfer.toAttendRecordTransfer(record);
	}

	private void recoverEmployeeLeave(AttendRecord record) {
		Employee employee = record.getEmployee();
		Date startDate = record.getStartDate();
		Date endDate = record.getEndDate();
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

		if (!CalendarUtils.overDateOfJoined(startDate, endDate, joinDate)) {
			logger.debug("Time Range is not over dateOfJoined");
			long years = CalendarUtils.getYearOfJoined(joinDate, startDate);
			logger.debug("Employee joined years : {}", years);
			Leavesetting leavesetting = leavesettingDao.findByTypeIdAndYear(
					record.getType().getId(), years);
			EmployeeLeave employeeLeave = employeeLeaveDao
					.findByEmployeeIdAndLeavesettingId(employee.getId(),
							leavesetting.getId());
			if (employeeLeave != null) {
				double oldUsedDays = employeeLeave.getUsedDays();
				double newUsedDays = oldUsedDays - duration;
				logger.debug("oldUsedDays:{}, newUsedDays:{} ", new Object[] {
						oldUsedDays, newUsedDays });
				employeeLeave.setUsedDays(newUsedDays);
				employeeLeaveDao.save(employeeLeave);
			}
		} else {
			if (startC.get(Calendar.DAY_OF_MONTH) == joinC
					.get(Calendar.DAY_OF_MONTH)) {
				logger.debug("Start date is joined date");
				long years = CalendarUtils.getYearOfJoined(joinDate, startDate);
				logger.debug("Employee joined years : {}", years);
				Leavesetting leavesetting = leavesettingDao
						.findByTypeIdAndYear(record.getType().getId(), years);
				EmployeeLeave employeeLeave = employeeLeaveDao
						.findByEmployeeIdAndLeavesettingId(employee.getId(),
								leavesetting.getId());
				if (employeeLeave != null) {
					double oldUsedDays = employeeLeave.getUsedDays();
					double newUsedDays = oldUsedDays - duration;
					logger.debug("oldUsedDays:{}, newUsedDays:{} ",
							new Object[] { oldUsedDays, newUsedDays });
					employeeLeave.setUsedDays(newUsedDays);
					employeeLeaveDao.save(employeeLeave);
				}
			} else if (endC.get(Calendar.DAY_OF_MONTH) == joinC
					.get(Calendar.DAY_OF_MONTH)) {
				logger.debug("End date is joined date");
				long years = CalendarUtils.getYearOfJoined(joinDate, startDate);
				logger.debug("Employee joined years : {}", years);
				Leavesetting leavesetting = leavesettingDao
						.findByTypeIdAndYear(record.getType().getId(), years);
				EmployeeLeave employeeLeave = employeeLeaveDao
						.findByEmployeeIdAndLeavesettingId(employee.getId(),
								leavesetting.getId());
				double newDuration = countDuration(joinDay, endDate);
				logger.debug("Next Year Duration : {}", newDuration);
				if (employeeLeave != null) {
					double oldUsedDays = employeeLeave.getUsedDays();
					double newUsedDays = oldUsedDays - (duration - newDuration);
					logger.debug("last year oldUsedDays:{}, newUsedDays:{} ",
							new Object[] { oldUsedDays, newUsedDays });
					employeeLeave.setUsedDays(newUsedDays);
					employeeLeaveDao.save(employeeLeave);
				}
				Leavesetting newLeavesetting = leavesettingDao
						.findByTypeIdAndYear(record.getType().getId(),
								(years + 1));
				employeeLeave = employeeLeaveDao
						.findByEmployeeIdAndLeavesettingId(employee.getId(),
								newLeavesetting.getId());
				if (employeeLeave != null) {
					double oldUsedDays = employeeLeave.getUsedDays();
					double newUsedDays = employeeLeave.getUsedDays()
							- newDuration;
					logger.debug("next year oldUsedDays:{}, newUsedDays:{} ",
							new Object[] { oldUsedDays, newUsedDays });
					employeeLeave.setUsedDays(newUsedDays);
					employeeLeaveDao.save(employeeLeave);
				}
			} else {
				Calendar calE = Calendar.getInstance();
				calE.setTime(joinDay);
				calE.add(Calendar.DATE, -1);
				calE.set(Calendar.AM_PM, 1);
				calE.set(Calendar.HOUR, 6);
				double pastDuration = countDuration(startDate, calE.getTime());

				long years = CalendarUtils.getYearOfJoined(joinDate, startDate);
				Leavesetting past = leavesettingDao.findByTypeIdAndYear(record
						.getType().getId(), years);
				EmployeeLeave employeeLeave = employeeLeaveDao
						.findByEmployeeIdAndLeavesettingId(employee.getId(),
								past.getId());
				if (employeeLeave != null) {
					employeeLeave
							.setUsedDays((employeeLeave.getUsedDays() - pastDuration));
					employeeLeaveDao.save(employeeLeave);
				}
				double newDuration = countDuration(joinDay, endDate);
				Leavesetting newLeavesetting = leavesettingDao
						.findByTypeIdAndYear(record.getType().getId(),
								(years + 1));
				employeeLeave = employeeLeaveDao
						.findByEmployeeIdAndLeavesettingId(employee.getId(),
								newLeavesetting.getId());
				if (employeeLeave != null) {
					double oldUsedDays = employeeLeave.getUsedDays();
					double newUsedDays = oldUsedDays - newDuration;
					logger.debug("last year oldUsedDays:{}, newUsedDays:{} ",
							new Object[] { oldUsedDays, newUsedDays });
					employeeLeave.setUsedDays(newUsedDays);
					employeeLeaveDao.save(employeeLeave);
				}
			}
		}
	}

	@Override
	public AttendRecordTransfer permit(long id) {
		AttendRecord record = attendRecordDao.findOne(id);
		if (record == null) {
			throw new AttendRecordNotFoundException(id);
		}
		record.setStatus(AttendRecordTransfer.Status.permit.name());
		record = attendRecordDao.save(record);
		com.google.api.services.calendar.model.Event calendarEvent = CalendarUtils
				.toEvent(record);
		calendarEventService.save(calendarEvent);
		return AttendRecordTransfer.toAttendRecordTransfer(record);
	}

	@Transactional
	@Override
	public void delete(Specification<AttendRecord> spec) {
		AttendRecord record = attendRecordDao.findOne(spec);
		if (record == null) {
			throw new AttendRecordNotFoundException();
		}
		if (!Status.pending.name().equals(record.getStatus())
				|| record.getEvents().size() > 1) {
			throw new ModificationNotAllowException();
		}
		recoverEmployeeLeave(record);
		try {
			attendRecordDao.delete(record);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new RemovingIntegrityViolationException(AttendRecord.class);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			throw new AttendRecordNotFoundException();
		}
	}

	private AttendRecordTransfer update(AttendRecord attendRecord,
			AttendRecordTransfer updated, boolean force) {
		if (attendRecord == null) {
			throw new AttendRecordNotFoundException();
		}
		if (!force
				&& (!Status.pending.name().equals(attendRecord.getStatus()) || attendRecord
						.getEvents().size() > 1)) {
			throw new ModificationNotAllowException();
		}

		recoverEmployeeLeave(attendRecord);
		attendRecord.setBookDate(new Date());
		setUpAttendRecord(updated, attendRecord);
		Employee applicant = attendRecord.getEmployee();
		assertStartDateAfterJoinDate(attendRecord.getStartDate(),
				applicant.getDateofjoined());
		assetEndDateAfterStartDate(attendRecord.getStartDate(),
				attendRecord.getEndDate());
		// assertStartDateNotOver30DaysFromNow(attendRecord.getStartDate());
		assertIsBusinessDays(attendRecord.getType(),
				attendRecord.getStartDate(), attendRecord.getEndDate());
		attendRecordDao.save(attendRecord);
		assertEnoughAvailableLeaveDays(attendRecord);
		return AttendRecordTransfer.toAttendRecordTransfer(attendRecord);
	}

	@Transactional
	@Override
	public AttendRecordTransfer update(Specification<AttendRecord> spec,
			AttendRecordTransfer updated) {
		AttendRecord attendRecord = attendRecordDao.findOne(spec);
		return update(attendRecord, updated, false);
	}

	@Override
	@Transactional
	public Metadata retrieveMetadataByEmployeeId(long id) {
		AttendRecordSpecification spec = new AttendRecordSpecification();
		spec.setApplicantId(id);
		Metadata metadata = new Metadata();
		for (transfer.AttendRecordTransfer.Status status : transfer.AttendRecordTransfer.Status
				.values()) {
			spec.setStatus(status.name());
			long count = attendRecordDao.count(spec);
			metadata.put(status.name(), count);
		}

		Iterable<AttendRecordType> types = attendRecordTypeDao.findAll();
		Iterator<AttendRecordType> iterator = types.iterator();
		spec = new AttendRecordSpecification();
		spec.setApplicantId(id);
		while (iterator.hasNext()) {
			AttendRecordType type = iterator.next();
			spec.setTypeName(type.getName());
			long count = attendRecordDao.count(spec);
			metadata.put(type.getName(), count);
		}
		return metadata;
	}

	@Transactional
	@Override
	public List<PermittedAttendRecord> findAllPermittedAttendRecords(
			Specification<AttendRecord> spec) {
		List<PermittedAttendRecord> transfers = new ArrayList<PermittedAttendRecord>();
		List<AttendRecord> attendRecords = attendRecordDao.findAll(spec);
		for (AttendRecord attendRecord : attendRecords) {
			transfers.add(PermittedAttendRecord
					.toPermittedAttendRecord(attendRecord));
		}
		return transfers;
	}
}