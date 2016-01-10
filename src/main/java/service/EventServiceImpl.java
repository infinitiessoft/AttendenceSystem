package service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import resources.specification.EventSpecification;
import transfer.AttendRecordTransfer.Status;
import transfer.AttendRecordTransfer.Type;
import transfer.EventTransfer;

import com.google.common.base.Strings;

import dao.EventDao;
import entity.Event;
import exceptions.DuplicateApproveException;
import exceptions.EventNotFoundException;

public class EventServiceImpl implements EventService {

	private EventDao eventDao;

	public EventServiceImpl(EventDao eventDao) {
		this.eventDao = eventDao;
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
		setUpEvent(updated, event);
		return toEventTransfer(eventDao.save(event));
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
