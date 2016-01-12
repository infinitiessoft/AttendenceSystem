package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import resources.specification.EventSpecification;
import transfer.AttendRecordTransfer;
import transfer.AttendRecordTransfer.Status;
import transfer.AttendRecordTransfer.Type;
import transfer.EventTransfer;
import transfer.EventTransfer.Action;
import calendar.CalendarEventDao;
import calendar.CalendarUtil;

import com.google.common.base.Strings;

import dao.AttendRecordDao;
import dao.EventDao;
import entity.AttendRecord;
import entity.Employee;
import entity.Event;
import exceptions.DuplicateApproveException;
import exceptions.EventNotFoundException;
import exceptions.InvalidActionException;

public class EventServiceImpl implements EventService {

	private static final Logger logger = LoggerFactory
			.getLogger(EventServiceImpl.class);
	private EventDao eventDao;
	private AttendRecordDao attendRecordDao;
	private CalendarEventDao calendarEventDao;

	public EventServiceImpl(EventDao eventDao, AttendRecordDao attendRecordDao,
			CalendarEventDao calendarEventDao) {
		this.eventDao = eventDao;
		this.attendRecordDao = attendRecordDao;
		this.calendarEventDao = calendarEventDao;
	}

	@Transactional
	@Override
	public EventTransfer retrieve(long id) {
		Event event = eventDao.findOne(id);
		if (event == null) {
			throw new EventNotFoundException(id);
		}
		return toEventTransfer(event);
	}

	@Override
	public void delete(long id) {
		try {
			eventDao.delete(id);
		} catch (NullPointerException e) {
			throw new EventNotFoundException(id);
		}
	}

	@Transactional
	@Override
	public EventTransfer save(EventTransfer event) {
		event.setId(null);
		Event dep = new Event();
		setUpEvent(event, dep);
		return toEventTransfer(eventDao.save(dep));
	}

	private void setUpEvent(EventTransfer transfer, Event dep) {
		if (transfer.isActionSet()) {
			dep.setAction(transfer.getAction());
			dep.setBookDate(new Date());
		}
	}

	@Transactional
	@Override
	public EventTransfer update(long id, EventTransfer updated) {
		Event event = eventDao.findOne(id);
		if (event == null) {
			throw new EventNotFoundException(id);
		}
		if (!Strings.isNullOrEmpty(event.getAction())) {
			throw new DuplicateApproveException();
		}
		Action action = null;
		try {
			action = EventTransfer.Action.valueOf(updated.getAction());
		} catch (IllegalArgumentException e) {
			throw new InvalidActionException(updated.getAction());
		}
		setUpEvent(updated, event);
		event = eventDao.save(event);

		// if event is being permited and leave duration is more than 1 day and
		// there is a manager the employee's manager response to then send it an
		// blank event too.
		AttendRecord record = event.getAttendRecord();
		logger.debug("action:{}", event.getAction());
		if (EventTransfer.Action.permit.equals(action)) {
			Double duration = record.getDuration();
			int size = record.getEvents().size();
			Employee employee = event.getEmployee().getEmployee();
			logger.debug("duration:{}, size:{}",
					new Object[] { duration, size });
			if (duration > 1.0d && size <= 1 && employee != null) {
				logger.debug("second event have not been sent, send it now");
				entity.Event newEvent = new entity.Event();
				newEvent.setAttendRecord(record);
				newEvent.setEmployee(employee);
				eventDao.save(newEvent);
			} else { // duration less than or equal to 1 day
				record = permit(record);
			}
		} else {
			logger.debug("set record status to 'reject'");
			record.setStatus(AttendRecordTransfer.Status.reject.name());
			record = attendRecordDao.save(record);
		}
		event.setAttendRecord(record);
		return toEventTransfer(event);
	}

	private AttendRecord permit(AttendRecord record) {
		record.setStatus(AttendRecordTransfer.Status.permit.name());
		record = attendRecordDao.save(record);
		com.google.api.services.calendar.model.Event calendarEvent = CalendarUtil
				.toEvent(record);
		calendarEventDao.save(calendarEvent);
		return record;
	}

	@Transactional
	@Override
	public Page<EventTransfer> findAll(EventSpecification spec,
			Pageable pageable) {
		List<EventTransfer> transfers = new ArrayList<EventTransfer>();
		Page<Event> events = eventDao.findAll(spec, pageable);
		for (Event event : events) {
			transfers.add(toEventTransfer(event));
		}
		Page<EventTransfer> rets = new PageImpl<EventTransfer>(transfers,
				pageable, events.getTotalElements());
		return rets;
	}

	private EventTransfer toEventTransfer(Event event) {
		EventTransfer ret = new EventTransfer();
		ret.setId(event.getId());
		ret.setAction(event.getAction());
		ret.setBookDate(event.getBookDate());
		EventTransfer.Employee approver = new EventTransfer.Employee();
		approver.setId(event.getEmployee().getId());
		approver.setName(event.getEmployee().getName());
		ret.setApprover(approver);
		EventTransfer.AttendRecord record = new EventTransfer.AttendRecord();
		record.setBookDate(event.getAttendRecord().getBookDate());
		record.setDuration(event.getAttendRecord().getDuration());
		record.setEndDate(event.getAttendRecord().getEndDate());
		record.setStartDate(event.getAttendRecord().getStartDate());
		record.setId(event.getAttendRecord().getId());
		record.setReason(event.getAttendRecord().getReason());
		Type type = new Type();
		type.setId(event.getAttendRecord().getType().getId());
		type.setName(event.getAttendRecord().getType().getName());
		record.setType(type);
		record.setStatus(Status.valueOf(event.getAttendRecord().getStatus()));
		ret.setRecord(record);
		EventTransfer.Employee applicant = new EventTransfer.Employee();
		applicant.setId(event.getAttendRecord().getEmployee().getId());
		applicant.setName(event.getAttendRecord().getEmployee().getName());
		record.setEmployee(applicant);

		return ret;
	}
}
