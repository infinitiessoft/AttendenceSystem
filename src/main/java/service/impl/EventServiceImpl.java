package service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import resources.specification.EventSpecification;
import service.AttendRecordService;
import service.EventService;
import service.MailService;
import transfer.AttendRecordTransfer;
import transfer.AttendRecordTransfer.Status;
import transfer.AttendRecordTransfer.Type;
import transfer.EventTransfer;
import transfer.EventTransfer.Action;
import transfer.Metadata;
import util.MailWritter;

import com.google.common.base.Strings;

import dao.AttendRecordTypeDao;
import dao.EmployeeDao;
import dao.EventDao;
import entity.AttendRecord;
import entity.AttendRecordType;
import entity.Employee;
import entity.Event;
import exceptions.DuplicateApproveException;
import exceptions.EmployeeNotFoundException;
import exceptions.EventNotFoundException;
import exceptions.InvalidActionException;
import exceptions.RemovingIntegrityViolationException;

public class EventServiceImpl implements EventService {

	private static final Logger logger = LoggerFactory
			.getLogger(EventServiceImpl.class);
	private EventDao eventDao;
	private AttendRecordTypeDao attendRecordTypeDao;
	private AttendRecordService attendRecordService;
	private EmployeeDao employeeDao;
	private MailService mailService;
	private MailWritter mailWritter;

	public EventServiceImpl(EventDao eventDao,
			AttendRecordTypeDao attendRecordTypeDao,
			AttendRecordService attendRecordService, EmployeeDao employeeDao,
			MailService mailService, MailWritter mailWritter) {
		this.attendRecordTypeDao = attendRecordTypeDao;
		this.eventDao = eventDao;
		this.attendRecordService = attendRecordService;
		this.employeeDao = employeeDao;
		this.mailService = mailService;
		this.mailWritter = mailWritter;
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
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new RemovingIntegrityViolationException(Event.class);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			throw new EventNotFoundException(id);
		}
	}

	@Transactional
	@Override
	public EventTransfer save(EventTransfer event) {
		event.setId(null);
		Event dep = new Event();
		setUpEvent(event, dep);

		return toEventTransfer(save(dep));
	}

	private Event save(Event event) {
		event = eventDao.save(event);
		try {
			notifyApprover(event.getEmployee(), event.getAttendRecord());
		} catch (Throwable t) {
			logger.warn("send mail failed", t);
		}
		return event;
	}

	private Event update(Event event) {
		return eventDao.save(event);
	}

	private void setUpEvent(EventTransfer transfer, Event dep) {
		if (transfer.isActionSet()) {
			dep.setAction(transfer.getAction());
			dep.setBookDate(new Date());
		}

		if (transfer.isRecordSet() && transfer.getRecord().isIdSet()) {
			AttendRecordTransfer recordTransfer = attendRecordService
					.retrieve(transfer.getRecord().getId());
			AttendRecord record = new AttendRecord();
			record.setBookDate(recordTransfer.getBookDate());
			record.setDuration(recordTransfer.getDuration());
			record.setEndDate(recordTransfer.getEndDate());
			record.setId(recordTransfer.getId());
			record.setReason(recordTransfer.getReason());
			record.setStartDate(recordTransfer.getStartDate());
			record.setStatus(recordTransfer.getStatus().name());
			Employee employee = new Employee();
			employee.setId(recordTransfer.getApplicant().getId());
			employee.setName(recordTransfer.getApplicant().getName());
			record.setEmployee(employee);
			AttendRecordType type = new AttendRecordType();
			type.setId(recordTransfer.getType().getId());
			type.setName(recordTransfer.getType().getName());
			record.setType(type);
			dep.setAttendRecord(record);
		}

		if (transfer.isApproverSet() && transfer.getApprover().isIdSet()) {
			Employee employee = employeeDao.findOne(transfer.getApprover()
					.getId());
			if (employee == null) {
				throw new EmployeeNotFoundException(transfer.getApprover()
						.getId());
			}
			dep.setEmployee(employee);
		}
	}

	@Transactional
	@Override
	public EventTransfer update(long id, EventTransfer updated) {
		Event event = eventDao.findOne(id);
		if (event == null) {
			throw new EventNotFoundException(id);
		}
		if (!EventTransfer.Action.pending.name().equals(event.getAction())) {
			throw new DuplicateApproveException();
		}
		Action action = null;
		try {
			action = EventTransfer.Action.valueOf(updated.getAction());
		} catch (IllegalArgumentException e) {
			throw new InvalidActionException(updated.getAction());
		}
		if (updated.isActionSet()) {
			event.setAction(updated.getAction());
			event.setBookDate(new Date());
		}

		event = update(event);

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
				save(newEvent);
			} else { // duration less than or equal to 1 day
				AttendRecordTransfer recordTransfer = attendRecordService
						.permit(record.getId());
				record.setStatus(recordTransfer.getStatus().name());
			}
		} else {
			logger.debug("set record status to 'reject'");
			AttendRecordTransfer recordTransfer = attendRecordService
					.reject(record.getId());
			record.setStatus(recordTransfer.getStatus().name());
		}
		event.setAttendRecord(record);
		return toEventTransfer(event);
	}

	private void notifyApprover(Employee approver, AttendRecord newEntry) {
		String subject = mailWritter.buildSubject(newEntry);
		String body = mailWritter.buildBody(newEntry);
		mailService.sendMail(approver.getEmail(), subject, body);
	}

	@Transactional
	@Override
	public Page<EventTransfer> findAll(EventSpecification spec,
			Pageable pageable) {
		List<EventTransfer> transfers = new ArrayList<EventTransfer>();
		Page<Event> events = eventDao.findAll(spec, pageable);
		for (Event event : events) {
			EventTransfer transfer = toEventTransfer(event);
			if(Strings.isNullOrEmpty(transfer.getAction())){
				transfer.setAction(Status.pending.name());
			}
			transfers.add(transfer);
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

	@Override
	@Transactional
	public Metadata retrieveMetadataByEmployeeId(final long id) {
		EventSpecification spec = new EventSpecification();
		spec.setApproverId(id);
		Metadata metadata = new Metadata();
		for (transfer.EventTransfer.Action action : transfer.EventTransfer.Action
				.values()) {
			spec.setAction(action.name());
			long count = eventDao.count(spec);
			metadata.put(action.name(), count);
		}

		Iterable<AttendRecordType> types = attendRecordTypeDao.findAll();
		Iterator<AttendRecordType> iterator = types.iterator();
		spec = new EventSpecification();
		spec.setApproverId(id);
		while (iterator.hasNext()) {
			AttendRecordType type = iterator.next();
			spec.setRecordTypeName(type.getName());
			long count = eventDao.count(spec);
			metadata.put(type.getName(), count);
		}
		return metadata;
	}
}
