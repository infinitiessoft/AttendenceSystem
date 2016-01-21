package resources;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Calendar;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import transfer.AttendRecordTransfer;
import assertion.AssertUtils;
import entity.PageModel;

public class EmployeeAttendRecordsResourceTest extends ResourceTest {

	@Test
	public void testFindAll() {
		Response response = target("employees").path("1").path("records")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<AttendRecordTransfer> rets = response
				.readEntity(new GenericType<PageModel<AttendRecordTransfer>>() {
				});
		assertEquals(2, rets.getTotalElements());
	}

	@Test
	public void testGetAttendRecord() {
		Response response = target("employees").path("1").path("records")
				.path("1").register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		AttendRecordTransfer transfer = response
				.readEntity(AttendRecordTransfer.class);
		assertEquals(1L, transfer.getId().longValue());
	}

	@Test
	public void testGetAttendRecordWithNotFoundException() {
		Response response = target("employees").path("1").path("records")
				.path("4").register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	// @Test
	// public void testDeleteAttendRecord() {
	// Response response = target("records").path("1")
	// .register(JacksonFeature.class).request()
	// .header("user", "demo").delete();
	// assertEquals(Status.OK.getStatusCode(), response.getStatus());
	// }
	//
	// @Test
	// public void testDeleteAttendRecordWithNotFoundException() {
	// Response response = target("records").path("4")
	// .register(JacksonFeature.class).request()
	// .header("user", "demo").delete();
	// AssertUtils.assertNotFound(response);
	// }

	@Test
	public void testSaveAttendRecord() throws ParseException {
		Calendar startDateC = Calendar.getInstance();
		startDateC.set(2015, 0, 7, 10, 0);
		Calendar endDateC = Calendar.getInstance();
		endDateC.set(2015, 0, 7, 18, 0);
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("employees").path("1").path("records")
				.register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").post(Entity.json(record));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		AttendRecordTransfer transfer = response
				.readEntity(AttendRecordTransfer.class);
		assertEquals(5l, transfer.getId().longValue());
		assertEquals(record.getType().getId(), transfer.getType().getId());
		assertEquals(record.getStartDate(), transfer.getStartDate());
		assertEquals(record.getEndDate(), transfer.getEndDate());
		assertEquals(record.getReason(), transfer.getReason());
		assertEquals(1l, transfer.getApplicant().getId().longValue());
		assertEquals(AttendRecordTransfer.Status.permit, transfer.getStatus());
	}

	@Test
	public void testSaveAttendRecordWithEndDateOverlayCrossYear()
			throws ParseException {
		Calendar startDateC = Calendar.getInstance();
		startDateC.set(2015, 4, 4, 10, 0);
		Calendar endDateC = Calendar.getInstance();
		endDateC.set(2015, 4, 5, 18, 0);
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("employees").path("1").path("records")
				.register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").post(Entity.json(record));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		AttendRecordTransfer transfer = response
				.readEntity(AttendRecordTransfer.class);
		assertEquals(7l, transfer.getId().longValue());
		assertEquals(record.getType().getId(), transfer.getType().getId());
		assertEquals(record.getStartDate(), transfer.getStartDate());
		assertEquals(record.getEndDate(), transfer.getEndDate());
		assertEquals(record.getReason(), transfer.getReason());
		assertEquals(1l, transfer.getApplicant().getId().longValue());
		assertEquals(AttendRecordTransfer.Status.permit, transfer.getStatus());
	}

	@Test
	public void testSaveAttendRecordWithStartDateOverlayCrossYear()
			throws ParseException {
		Calendar startDateC = Calendar.getInstance();
		startDateC.set(2015, 4, 5, 10, 0);
		Calendar endDateC = Calendar.getInstance();
		endDateC.set(2015, 4, 7, 18, 0);
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("employees").path("1").path("records")
				.register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").post(Entity.json(record));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		AttendRecordTransfer transfer = response
				.readEntity(AttendRecordTransfer.class);
		assertEquals(4l, transfer.getId().longValue());
		assertEquals(record.getType().getId(), transfer.getType().getId());
		assertEquals(record.getStartDate(), transfer.getStartDate());
		assertEquals(record.getEndDate(), transfer.getEndDate());
		assertEquals(record.getReason(), transfer.getReason());
		assertEquals(1l, transfer.getApplicant().getId().longValue());
		assertEquals(AttendRecordTransfer.Status.permit, transfer.getStatus());
	}

	@Test
	public void testSaveAttendRecordWithCrossYear() throws ParseException {
		Calendar startDateC = Calendar.getInstance();
		startDateC.set(2015, 4, 4, 10, 0);
		Calendar endDateC = Calendar.getInstance();
		endDateC.set(2015, 4, 7, 18, 0);
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("employees").path("1").path("records")
				.register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").post(Entity.json(record));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		AttendRecordTransfer transfer = response
				.readEntity(AttendRecordTransfer.class);
		assertEquals(6l, transfer.getId().longValue());
		assertEquals(record.getType().getId(), transfer.getType().getId());
		assertEquals(record.getStartDate(), transfer.getStartDate());
		assertEquals(record.getEndDate(), transfer.getEndDate());
		assertEquals(record.getReason(), transfer.getReason());
		assertEquals(1l, transfer.getApplicant().getId().longValue());
		assertEquals(AttendRecordTransfer.Status.permit, transfer.getStatus());
	}

	@Test
	public void testSaveAttendRecordWithIncludeMarkupDay() {
		Calendar startDateC = Calendar.getInstance();
		startDateC.set(2015, 0, 6, 10, 0);
		Calendar endDateC = Calendar.getInstance();
		endDateC.set(2015, 0, 6, 18, 0);
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("employees").path("1").path("records")
				.register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").post(Entity.json(record));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		AttendRecordTransfer transfer = response
				.readEntity(AttendRecordTransfer.class);
		assertEquals(3l, transfer.getId().longValue());
		assertEquals(record.getType().getId(), transfer.getType().getId());
		assertEquals(record.getStartDate(), transfer.getStartDate());
		assertEquals(record.getEndDate(), transfer.getEndDate());
		assertEquals(record.getReason(), transfer.getReason());
		assertEquals(1l, transfer.getApplicant().getId().longValue());
		assertEquals(AttendRecordTransfer.Status.permit, transfer.getStatus());
	}

	@Test
	public void testSaveAttendRecordWithIncludeHoliday() {
		Calendar startDateC = Calendar.getInstance();
		startDateC.set(2015, 0, 4, 10, 0);
		Calendar endDateC = Calendar.getInstance();
		endDateC.set(2015, 0, 4, 18, 0);
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("employees").path("1").path("records")
				.register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").post(Entity.json(record));
		AssertUtils.assertBadRequest(response);
	}

	@Test
	public void testSaveAttendRecordWithNoEnoughAvailableLeave() {
		Calendar startDateC = Calendar.getInstance();
		startDateC.set(2015, 1, 10, 10, 0);
		Calendar endDateC = Calendar.getInstance();
		endDateC.set(2015, 1, 31, 18, 0);
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("employees").path("1").path("records")
				.register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").post(Entity.json(record));
		AssertUtils.assertBadRequest(response);
	}

	@Override
	Class<?>[] getResource() {
		return new Class<?>[] { EmployeesResource.class };
	}

}
