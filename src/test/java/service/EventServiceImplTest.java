package service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.api.Invocation;
import org.jmock.lib.action.CustomAction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import resources.specification.EventSpecification;
import resources.specification.SimplePageRequest;
import service.impl.EventServiceImpl;
import transfer.AttendRecordTransfer;
import transfer.EventTransfer;
import util.MailWritter;
import dao.EmployeeDao;
import dao.EventDao;
import entity.AttendRecord;
import entity.AttendRecordType;
import entity.Employee;
import entity.Event;

public class EventServiceImplTest extends ServiceTest {

	private EventDao eventDao;
	private AttendRecordService attendRecordService;
	private EmployeeDao employeeDao;
	private MailService mailService;
	private MailWritter writter;
	private EventServiceImpl eventService;

	private Event event;
	private Employee approver;
	private Employee employee;
	private AttendRecord record;
	private AttendRecordType type;

	@Before
	public void setUp() throws Exception {
		writter = context.mock(MailWritter.class);
		eventDao = context.mock(EventDao.class);
		attendRecordService = context.mock(AttendRecordService.class);
		employeeDao = context.mock(EmployeeDao.class);
		mailService = context.mock(MailService.class);
		eventService = new EventServiceImpl(eventDao, attendRecordService,
				employeeDao, mailService, writter);

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

		final AttendRecordTransfer transfer = AttendRecordTransfer
				.toAttendRecordTransfer(record);
		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordService).retrieve(record.getId());
				will(returnValue(transfer));
				exactly(1).of(employeeDao).findOne(
						newEntry.getApprover().getId());
				will(returnValue(employee));
				exactly(1).of(eventDao).save(with(any(Event.class)));
				will(returnValue(event));
				exactly(1).of(writter).buildBody(record);
				will(returnValue("body"));

				exactly(1).of(writter).buildSubject(record);
				will(returnValue("subject"));

				exactly(1).of(mailService).sendMail(approver.getEmail(),
						"subject", "body");
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

				exactly(1).of(attendRecordService).permit(record.getId());
				will(with(new CustomAction("permit") {

					@Override
					public Object invoke(Invocation invocation)
							throws Throwable {
						record.setStatus("permit");
						return AttendRecordTransfer
								.toAttendRecordTransfer(record);
					}
				}));
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
