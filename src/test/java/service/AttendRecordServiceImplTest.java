package service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jmock.Expectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import resources.specification.AttendRecordSpecification;
import resources.specification.CalendarEventSpecification;
import resources.specification.SimplePageRequest;
import service.impl.AttendRecordServiceImpl;
import transfer.AttendRecordTransfer;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

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
import exceptions.ModificationNotAllowException;

public class AttendRecordServiceImplTest extends ServiceTest {

	private AttendRecordServiceImpl attendrecordService;

	private Employee employee;
	private AttendRecord record;
	private AttendRecordType type;
	private Leavesetting leavesetting;
	private EmployeeLeave leave;

	private AttendRecordDao attendRecordDao;
	private AttendRecordTypeDao attendRecordTypeDao;
	private EmployeeDao employeeDao;
	private EventDao eventDao;
	private MailService mailService;
	private CalendarEventService calendarEventService;
	private LeavesettingDao leavesettingDao;
	private EmployeeLeaveDao employeeLeaveDao;
	private List<Event> events;

	// private Role admin;

	@Before
	public void setUp() throws Exception {
		leavesettingDao = context.mock(LeavesettingDao.class);
		employeeLeaveDao = context.mock(EmployeeLeaveDao.class);
		attendRecordTypeDao = context.mock(AttendRecordTypeDao.class);
		attendRecordDao = context.mock(AttendRecordDao.class);
		calendarEventService = context.mock(CalendarEventService.class);
		employeeDao = context.mock(EmployeeDao.class);
		mailService = context.mock(MailService.class);
		eventDao = context.mock(EventDao.class);
		attendrecordService = new AttendRecordServiceImpl(attendRecordDao,
				attendRecordTypeDao, employeeDao, eventDao, mailService,
				calendarEventService, leavesettingDao, employeeLeaveDao);

		Calendar dateofjoinedC = Calendar.getInstance();
		dateofjoinedC.set(2015, 1, 1, 0, 0, 0);

		employee = new Employee();
		employee.setId(1L);
		employee.setUsername("demo");
		employee.setName("demo");
		employee.setDateofjoined(dateofjoinedC.getTime());

		type = new AttendRecordType();
		type.setId(1L);
		type.setName("sick");

		leavesetting = new Leavesetting();
		leavesetting.setId(1L);
		leavesetting.setName("sick_1");
		leavesetting.setYear(1L);
		leavesetting.setDays(3d);
		leavesetting.setType(type);

		leave = new EmployeeLeave();
		leave.setId(1l);
		leave.setEmployee(employee);
		leave.setLeavesetting(leavesetting);
		leave.setUsedDays(1d);

		Calendar bookDateC = Calendar.getInstance();
		bookDateC.set(2015, 1, 2, 0, 0, 0);

		Calendar startDateC = Calendar.getInstance();
		startDateC.set(2015, 1, 3, 10, 0, 0);

		Calendar endDateC = Calendar.getInstance();
		endDateC.set(2015, 1, 3, 18, 0, 0);

		record = new AttendRecord();
		record.setId(1L);
		record.setReason("reason");
		record.setEmployee(employee);
		record.setType(type);
		record.setBookDate(bookDateC.getTime());
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setDuration(1d);
		record.setStatus("permit");

		events = new ArrayList<Event>();
		Event event = new Event();
		EventDateTime start = new EventDateTime();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(2015, 1, 4, 0, 0, 0);
		DateTime startDateTime = new DateTime(startCalendar.getTime());
		start.setDate(startDateTime);
		event.setStart(start);

		EventDateTime end = new EventDateTime();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(2015, 1, 6, 23, 59, 0);
		DateTime endDateTime = new DateTime(endCalendar.getTime());
		end.setDate(endDateTime);
		event.setEnd(end);
		event.setSummary("\u88dc\u73ed");
		events.add(event);

		Event event1 = new Event();
		event1.setSummary("\u6625\u5047");
		event1.setStart(start);
		event1.setEnd(end);
		events.add(event1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieve() {
		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findOne(1L);
				will(returnValue(record));
			}
		});
		AttendRecordTransfer ret = attendrecordService.retrieve(1);
		assertEquals(record.getId(), ret.getId());
		assertEquals(record.getEndDate(), ret.getEndDate());
		assertEquals(record.getBookDate(), ret.getBookDate());
		assertEquals(record.getStartDate(), ret.getStartDate());
		assertEquals(record.getReason(), ret.getReason());
		assertEquals(record.getDuration(), ret.getDuration());
		assertEquals(record.getEmployee().getId(), ret.getApplicant().getId());
		assertEquals(record.getType().getId(), ret.getType().getId());
		assertEquals(record.getStatus(), ret.getStatus().name());
	}

	@Test
	public void testRetrieveBySpecification() {
		final AttendRecordSpecification spec = new AttendRecordSpecification();
		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findOne(spec);
				will(returnValue(record));
			}
		});
		AttendRecordTransfer ret = attendrecordService.retrieve(spec);
		assertEquals(record.getId(), ret.getId());
		assertEquals(record.getEndDate(), ret.getEndDate());
		assertEquals(record.getBookDate(), ret.getBookDate());
		assertEquals(record.getStartDate(), ret.getStartDate());
		assertEquals(record.getReason(), ret.getReason());
		assertEquals(record.getDuration(), ret.getDuration());
		assertEquals(record.getEmployee().getId(), ret.getApplicant().getId());
		assertEquals(record.getType().getId(), ret.getType().getId());
		assertEquals(record.getStatus(), ret.getStatus().name());
	}

	@Test
	public void testForceDelete() {
		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findOne(1L);
				will(returnValue(record));

				exactly(1).of(leavesettingDao).findByTypeIdAndYear(1L, 1L);
				will(returnValue(leavesetting));

				exactly(1).of(employeeLeaveDao)
						.findByEmployeeIdAndLeavesettingId(1L, 1L);
				will(returnValue(leave));

				exactly(1).of(employeeLeaveDao).save(leave);
				will(returnValue(leave));

				exactly(1).of(attendRecordDao).delete(record);
			}
		});
		attendrecordService.delete(1l, true);
	}

	@Test
	public void testDeleteBySpecification() {
		record.setStatus("pending");
		final AttendRecordSpecification spec = new AttendRecordSpecification();
		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findOne(spec);
				will(returnValue(record));

				exactly(1).of(leavesettingDao).findByTypeIdAndYear(1L, 1L);
				will(returnValue(leavesetting));

				exactly(1).of(employeeLeaveDao)
						.findByEmployeeIdAndLeavesettingId(1L, 1L);
				will(returnValue(leave));

				exactly(1).of(employeeLeaveDao).save(leave);
				will(returnValue(leave));

				exactly(1).of(attendRecordDao).delete(record);
			}
		});
		attendrecordService.delete(spec);
	}

	@Test(expected = ModificationNotAllowException.class)
	public void testDelete() {
		record.setStatus("permit");
		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findOne(1L);
				will(returnValue(record));

				exactly(1).of(leavesettingDao).findByTypeIdAndYear(1L, 1L);
				will(returnValue(leavesetting));

				exactly(1).of(employeeLeaveDao)
						.findByEmployeeIdAndLeavesettingId(1L, 1L);
				will(returnValue(leave));

				exactly(1).of(employeeLeaveDao).save(leave);
				will(returnValue(leave));

				exactly(1).of(attendRecordDao).delete(record);
			}
		});
		attendrecordService.delete(1l, false);
	}

	@Test
	public void testSaveWithApprover() {
		final entity.Event event = new entity.Event();

		Employee approver = new Employee();
		approver.setId(1L);
		approver.setUsername("approver");
		approver.setDateofjoined(new Date());
		approver.setEmail("email");
		employee.setEmployee(approver);

		event.setEmployee(approver);
		event.setAttendRecord(record);
		event.setBookDate(new Date());
		event.setId(1L);

		AttendRecordTransfer newEntry = new AttendRecordTransfer();
		newEntry.setReason("new reason");
		newEntry.setStartDate(record.getStartDate());
		newEntry.setEndDate(record.getEndDate());
		AttendRecordTransfer.Type typeTransfer = new AttendRecordTransfer.Type();
		typeTransfer.setId(type.getId());
		newEntry.setType(typeTransfer);
		AttendRecordTransfer.Employee applicant = new AttendRecordTransfer.Employee();
		applicant.setId(employee.getId());
		newEntry.setApplicant(applicant);

		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordTypeDao).findOne(type.getId());
				will(returnValue(type));

				exactly(1).of(employeeDao).findOne(employee.getId());
				will(returnValue(employee));

				exactly(1).of(leavesettingDao).findByTypeIdAndYear(
						type.getId(), 1L);
				will(returnValue(leavesetting));

				exactly(1).of(employeeLeaveDao)
						.findByEmployeeIdAndLeavesettingId(employee.getId(),
								leavesetting.getId());
				will(returnValue(leave));

				exactly(1).of(employeeLeaveDao).save(leave);
				will(returnValue(leave));

				exactly(1).of(attendRecordDao).save(
						with(any(AttendRecord.class)));
				will(returnValue(record));

				exactly(1).of(calendarEventService).findAll(
						with(any(CalendarEventSpecification.class)),
						with(Expectations.aNull(Pageable.class)));
				will(returnValue(events));

				exactly(1).of(calendarEventService)
						.save(with(any(Event.class)));

				exactly(1).of(eventDao).save(with(any(entity.Event.class)));
				will(returnValue(event));

				exactly(1).of(mailService).sendMail(with(any(String.class)),
						with(any(String.class)), with(any(String.class)));
			}
		});
		AttendRecordTransfer ret = attendrecordService.save(newEntry);
		assertEquals(record.getId(), ret.getId());
		assertEquals(record.getEndDate(), ret.getEndDate());
		assertEquals(record.getBookDate(), ret.getBookDate());
		assertEquals(record.getStartDate(), ret.getStartDate());
		assertEquals(record.getReason(), ret.getReason());
		assertEquals(record.getDuration(), ret.getDuration());
		assertEquals(record.getEmployee().getId(), ret.getApplicant().getId());
		assertEquals(record.getType().getId(), ret.getType().getId());
		assertEquals(record.getStatus(), ret.getStatus().name());
	}

	@Test
	public void testSaveWithFullDaySickLeave() {
		AttendRecordTransfer newEntry = new AttendRecordTransfer();
		newEntry.setReason("new reason");
		newEntry.setStartDate(record.getStartDate());
		newEntry.setEndDate(record.getEndDate());
		AttendRecordTransfer.Type typeTransfer = new AttendRecordTransfer.Type();
		typeTransfer.setId(type.getId());
		newEntry.setType(typeTransfer);
		AttendRecordTransfer.Employee applicant = new AttendRecordTransfer.Employee();
		applicant.setId(employee.getId());
		newEntry.setApplicant(applicant);

		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordTypeDao).findOne(type.getId());
				will(returnValue(type));

				exactly(1).of(employeeDao).findOne(employee.getId());
				will(returnValue(employee));

				exactly(1).of(leavesettingDao).findByTypeIdAndYear(
						type.getId(), 1L);
				will(returnValue(leavesetting));

				exactly(1).of(employeeLeaveDao)
						.findByEmployeeIdAndLeavesettingId(employee.getId(),
								leavesetting.getId());
				will(returnValue(leave));

				exactly(1).of(employeeLeaveDao).save(leave);
				will(returnValue(leave));

				exactly(1).of(attendRecordDao).save(
						with(any(AttendRecord.class)));
				will(returnValue(record));

				exactly(1).of(calendarEventService).findAll(
						with(any(CalendarEventSpecification.class)),
						with(Expectations.aNull(Pageable.class)));
				will(returnValue(events));

				exactly(1).of(calendarEventService)
						.save(with(any(Event.class)));
			}
		});
		AttendRecordTransfer ret = attendrecordService.save(newEntry);
		assertEquals(record.getId(), ret.getId());
		assertEquals(record.getEndDate(), ret.getEndDate());
		assertEquals(record.getBookDate(), ret.getBookDate());
		assertEquals(record.getStartDate(), ret.getStartDate());
		assertEquals(record.getReason(), ret.getReason());
		assertEquals(record.getDuration(), ret.getDuration());
		assertEquals(record.getEmployee().getId(), ret.getApplicant().getId());
		assertEquals(record.getType().getId(), ret.getType().getId());
		assertEquals(record.getStatus(), ret.getStatus().name());
	}

	@Test
	public void testUpdateWithSpecification() {
		final AttendRecordSpecification spec = new AttendRecordSpecification();
		AttendRecordTransfer newEntry = new AttendRecordTransfer();
		newEntry.setReason("new reason");
		record.setStatus("pending");

		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findOne(spec);
				will(returnValue(record));

				exactly(1).of(attendRecordDao).save(record);
				will(returnValue(record));

				exactly(2).of(leavesettingDao).findByTypeIdAndYear(1L, 1L);
				will(returnValue(leavesetting));

				exactly(2).of(employeeLeaveDao)
						.findByEmployeeIdAndLeavesettingId(1L, 1L);
				will(returnValue(leave));

				exactly(2).of(employeeLeaveDao).save(leave);
				will(returnValue(leave));

				exactly(1).of(calendarEventService).findAll(
						with(any(CalendarEventSpecification.class)),
						with(Expectations.aNull(Pageable.class)));
				will(returnValue(events));
			}
		});
		AttendRecordTransfer ret = attendrecordService.update(spec, newEntry);
		assertEquals(record.getId(), ret.getId());
		assertEquals(record.getEndDate(), ret.getEndDate());
		assertEquals(record.getBookDate(), ret.getBookDate());
		assertEquals(record.getStartDate(), ret.getStartDate());
		assertEquals(newEntry.getReason(), ret.getReason());
		assertEquals(record.getDuration(), ret.getDuration());
		assertEquals(record.getEmployee().getId(), ret.getApplicant().getId());
		assertEquals(record.getType().getId(), ret.getType().getId());
		assertEquals(record.getStatus(), ret.getStatus().name());
	}

	@Test
	public void testUpdate() {
		AttendRecordTransfer newEntry = new AttendRecordTransfer();
		newEntry.setReason("new reason");
		record.setStatus("pending");

		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findOne(record.getId());
				will(returnValue(record));

				exactly(1).of(attendRecordDao).save(record);
				will(returnValue(record));

				exactly(2).of(leavesettingDao).findByTypeIdAndYear(1L, 1L);
				will(returnValue(leavesetting));

				exactly(2).of(employeeLeaveDao)
						.findByEmployeeIdAndLeavesettingId(1L, 1L);
				will(returnValue(leave));

				exactly(2).of(employeeLeaveDao).save(leave);
				will(returnValue(leave));

				exactly(1).of(calendarEventService).findAll(
						with(any(CalendarEventSpecification.class)),
						with(Expectations.aNull(Pageable.class)));
				will(returnValue(events));
			}
		});
		AttendRecordTransfer ret = attendrecordService.update(record.getId(),
				newEntry, true);
		assertEquals(record.getId(), ret.getId());
		assertEquals(record.getEndDate(), ret.getEndDate());
		assertEquals(record.getBookDate(), ret.getBookDate());
		assertEquals(record.getStartDate(), ret.getStartDate());
		assertEquals(newEntry.getReason(), ret.getReason());
		assertEquals(record.getDuration(), ret.getDuration());
		assertEquals(record.getEmployee().getId(), ret.getApplicant().getId());
		assertEquals(record.getType().getId(), ret.getType().getId());
		assertEquals(record.getStatus(), ret.getStatus().name());
	}

	@Test(expected = ModificationNotAllowException.class)
	public void testUpdateWithModificationNotAllow() {
		AttendRecordTransfer newEntry = new AttendRecordTransfer();
		newEntry.setReason("new reason");

		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findOne(record.getId());
				will(returnValue(record));

				exactly(1).of(attendRecordDao).save(record);
				will(returnValue(record));

				exactly(1).of(calendarEventService).findAll(
						with(any(CalendarEventSpecification.class)),
						with(Expectations.aNull(Pageable.class)));
				will(returnValue(events));
			}
		});
		AttendRecordTransfer ret = attendrecordService.update(record.getId(),
				newEntry, false);
		assertEquals(record.getId(), ret.getId());
		assertEquals(record.getEndDate(), ret.getEndDate());
		assertEquals(record.getBookDate(), ret.getBookDate());
		assertEquals(record.getStartDate(), ret.getStartDate());
		assertEquals(newEntry.getReason(), ret.getReason());
		assertEquals(record.getDuration(), ret.getDuration());
		assertEquals(record.getEmployee().getId(), ret.getApplicant().getId());
		assertEquals(record.getType().getId(), ret.getType().getId());
		assertEquals(record.getStatus(), ret.getStatus().name());
	}

	@Test
	public void testFindAll() {
		final AttendRecordSpecification spec = new AttendRecordSpecification();
		final SimplePageRequest pageable = new SimplePageRequest(0, 20, "id",
				"ASC");

		final List<AttendRecord> attendrecords = new ArrayList<AttendRecord>();
		attendrecords.add(record);
		final Page<AttendRecord> page = new PageImpl<AttendRecord>(
				attendrecords);

		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findAll(spec, pageable);
				will(returnValue(page));
			}
		});
		Page<AttendRecordTransfer> rets = attendrecordService.findAll(spec,
				pageable);
		assertEquals(1, rets.getTotalElements());
		AttendRecordTransfer ret = rets.iterator().next();
		assertEquals(record.getId(), ret.getId());
		assertEquals(record.getEndDate(), ret.getEndDate());
		assertEquals(record.getBookDate(), ret.getBookDate());
		assertEquals(record.getStartDate(), ret.getStartDate());
		assertEquals(record.getReason(), ret.getReason());
		assertEquals(record.getDuration(), ret.getDuration());
		assertEquals(record.getEmployee().getId(), ret.getApplicant().getId());
		assertEquals(record.getType().getId(), ret.getType().getId());
		assertEquals(record.getStatus(), ret.getStatus().name());
	}

	@Test
	public void testReject() {
		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findOne(1L);
				will(returnValue(record));

				exactly(1).of(leavesettingDao).findByTypeIdAndYear(1L, 1L);
				will(returnValue(leavesetting));

				exactly(1).of(employeeLeaveDao)
						.findByEmployeeIdAndLeavesettingId(1L, 1L);
				will(returnValue(leave));

				exactly(1).of(employeeLeaveDao).save(leave);
				will(returnValue(leave));

				exactly(1).of(attendRecordDao).save(record);
				will(returnValue(record));
			}
		});
		attendrecordService.reject(1l);
	}

	@Test
	public void testPermit() {
		context.checking(new Expectations() {

			{
				exactly(1).of(attendRecordDao).findOne(1L);
				will(returnValue(record));

				exactly(1).of(calendarEventService)
						.save(with(any(Event.class)));

				exactly(1).of(attendRecordDao).save(record);
				will(returnValue(record));
			}
		});
		attendrecordService.permit(1l);
	}
}
