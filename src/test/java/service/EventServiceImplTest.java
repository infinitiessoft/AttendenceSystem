package service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import resources.specification.EventSpecification;
import resources.specification.SimplePageRequest;
import service.impl.EventServiceImpl;
import transfer.EventTransfer;
import util.MailUtils;
import dao.AttendRecordDao;
import dao.EmployeeDao;
import dao.EmployeeLeaveDao;
import dao.EventDao;
import dao.LeavesettingDao;
import entity.AttendRecord;
import entity.AttendRecordType;
import entity.Employee;
import entity.Event;

public class EventServiceImplTest {

	protected Mockery context = new JUnit4Mockery() {

		{
			setThreadingPolicy(new Synchroniser());
		}
	};

	private EventDao eventDao;
	private AttendRecordDao attendRecordDao;
	private CalendarEventService calendarEventService;
	private LeavesettingDao leavesettingDao;
	private EmployeeLeaveDao employeeLeaveDao;
	private EmployeeDao employeeDao;
	private MailService mailService;
	private EventServiceImpl eventService;

	private Event event;
	private Employee approver;
	private Employee employee;
	private AttendRecord record;
	private AttendRecordType type;

	@Before
	public void setUp() throws Exception {
		eventDao = context.mock(EventDao.class);
		attendRecordDao = context.mock(AttendRecordDao.class);
		calendarEventService = context.mock(CalendarEventService.class);
		leavesettingDao = context.mock(LeavesettingDao.class);
		employeeLeaveDao = context.mock(EmployeeLeaveDao.class);
		employeeDao = context.mock(EmployeeDao.class);
		mailService = context.mock(MailService.class);
		eventService = new EventServiceImpl(eventDao, attendRecordDao,
				calendarEventService, leavesettingDao, employeeLeaveDao,
				employeeDao, mailService);

		approver = new Employee();
		approver.setId(2L);
		approver.setUsername("admin");
		approver.setName("admin");
		approver.setEmail("email");

		employee = new Employee();
		employee.setId(1L);
		employee.setName("employee");
		employee.setUsername("employee");

		type = new AttendRecordType();
		type.setId(1L);
		type.setName("annual");

		record = new AttendRecord();
		record.setId(1L);
		record.setType(type);
		record.setEmployee(employee);
		record.setReason("reason");
		record.setStartDate(new Date());
		record.setEndDate(new Date());
		record.setBookDate(new Date());
		record.setStatus("permit");
		record.setDuration(1d);

		event = new Event();
		event.setId(1L);
		event.setAction("permit");
		event.setBookDate(new Date());
		event.setAttendRecord(record);
		event.setEmployee(approver);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(eventDao).findOne(1L);
				will(returnValue(event));
			}
		});
		EventTransfer ret = eventService.retrieve(1);
		assertEquals(event.getId(), ret.getId());
		assertEquals(event.getAction(), ret.getAction());
		assertEquals(event.getBookDate(), ret.getBookDate());
		assertEquals(event.getEmployee().getId(), ret.getApprover().getId());
		assertEquals(event.getAttendRecord().getEmployee().getId(), ret
				.getRecord().getEmployee().getId());
	}

	@Test
	public void testDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(eventDao).delete(1L);
			}
		});
		eventService.delete(1l);
	}

	@Test
	public void testSave() {
		final EventTransfer newEntry = new EventTransfer();
		EventTransfer.Employee approverTransfer = new EventTransfer.Employee();
		approverTransfer.setId(this.approver.getId());
		EventTransfer.AttendRecord recordTransfer = new EventTransfer.AttendRecord();
		recordTransfer.setId(this.record.getId());
		newEntry.setApprover(approverTransfer);
		newEntry.setRecord(recordTransfer);
		final String subject = MailUtils.buildSubject(record);
		final String body = MailUtils.buildBody(record);

		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findOne(
						newEntry.getRecord().getId());
				will(returnValue(record));
				exactly(1).of(employeeDao).findOne(
						newEntry.getApprover().getId());
				will(returnValue(employee));
				exactly(1).of(eventDao).save(with(any(Event.class)));
				will(returnValue(event));
				exactly(1).of(mailService).sendMail(approver.getEmail(),
						subject, body);
			}
		});
		EventTransfer ret = eventService.save(newEntry);
		assertEquals(event.getId(), ret.getId());
		assertEquals(event.getAction(), ret.getAction());
		assertEquals(event.getBookDate(), ret.getBookDate());
		assertEquals(event.getEmployee().getId(), ret.getApprover().getId());
		assertEquals(event.getAttendRecord().getEmployee().getId(), ret
				.getRecord().getEmployee().getId());
	}

	@Test
	public void testUpdateActionPermitAndNoFurtherAuditor() {
		event.setAction(null);
		final EventTransfer newEntry = new EventTransfer();
		newEntry.setAction("permit");
		context.checking(new Expectations() {

			{
				exactly(1).of(eventDao).save(event);
				will(returnValue(event));

				exactly(1).of(eventDao).findOne(1l);
				will(returnValue(event));

				exactly(1).of(attendRecordDao).save(record);
				will(returnValue(record));

				exactly(1)
						.of(calendarEventService)
						.save(with(any(com.google.api.services.calendar.model.Event.class)));
			}
		});
		EventTransfer ret = eventService.update(event.getId(), newEntry);
		assertEquals(1l, ret.getId().longValue());
		assertEquals(event.getId(), ret.getId());
		assertEquals(event.getAction(), ret.getAction());
		assertEquals(event.getBookDate(), ret.getBookDate());
		assertEquals(event.getEmployee().getId(), ret.getApprover().getId());
		assertEquals(event.getAttendRecord().getEmployee().getId(), ret
				.getRecord().getEmployee().getId());
	}

	@Test
	public void testFindAll() {
		final EventSpecification spec = new EventSpecification();
		final SimplePageRequest pageable = new SimplePageRequest(0, 20, "id",
				"ASC");
		final List<Event> events = new ArrayList<Event>();
		events.add(event);
		final Page<Event> page = new PageImpl<Event>(events);
		context.checking(new Expectations() {

			{
				exactly(1).of(eventDao).findAll(spec, pageable);
				will(returnValue(page));
			}
		});
		Page<EventTransfer> rets = eventService.findAll(spec, pageable);
		assertEquals(1, rets.getTotalElements());
		EventTransfer ret = rets.iterator().next();
		assertEquals(event.getId(), ret.getId());
		assertEquals(event.getAction(), ret.getAction());
		assertEquals(event.getBookDate(), ret.getBookDate());
		assertEquals(event.getEmployee().getId(), ret.getApprover().getId());
		assertEquals(event.getAttendRecord().getEmployee().getId(), ret
				.getRecord().getEmployee().getId());
	}
}
