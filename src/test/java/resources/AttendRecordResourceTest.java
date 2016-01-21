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

public class AttendRecordResourceTest extends ResourceTest {

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

		Response response = target("records").register(JacksonFeature.class)
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

		Response response = target("records").register(JacksonFeature.class)
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

		Response response = target("records").register(JacksonFeature.class)
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

		Response response = target("records").register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").post(Entity.json(record));
		AssertUtils.assertBadRequest(response);
	}

	@Test
	public void testUpdateAttendRecord() {
		Calendar startDateC = Calendar.getInstance();
		startDateC.set(2015, 1, 10, 10, 0);
		Calendar endDateC = Calendar.getInstance();
		endDateC.set(2015, 1, 10, 18, 0);
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("records").path("1")
				.register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").put(Entity.json(record));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		AttendRecordTransfer transfer = response
				.readEntity(AttendRecordTransfer.class);
		assertEquals(1L, transfer.getId().longValue());
		assertEquals(record.getType().getId(), transfer.getType().getId());
		assertEquals(record.getStartDate(), transfer.getStartDate());
		assertEquals(record.getEndDate(), transfer.getEndDate());
		assertEquals(record.getReason(), transfer.getReason());
		assertEquals(1l, transfer.getApplicant().getId().longValue());
		assertEquals(AttendRecordTransfer.Status.pending, transfer.getStatus());
	}

	@Test
	public void testUpdateAttendRecordWithNotFoundException() {
		Calendar startDateC = Calendar.getInstance();
		startDateC.set(2015, 1, 10, 10, 0);
		Calendar endDateC = Calendar.getInstance();
		endDateC.set(2015, 1, 10, 18, 0);
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Employee applicant = new AttendRecordTransfer.Employee();
		applicant.setId(1L);
		record.setApplicant(applicant);
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("records").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(record));
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testFindAllRecords() {
		Response response = target("records").register(JacksonFeature.class)
				.request().header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<AttendRecordTransfer> rets = response
				.readEntity(new GenericType<PageModel<AttendRecordTransfer>>() {
				});
		assertEquals(2, rets.getTotalElements());
	}

	@Override
	Class<?> getResource() {
		return AttendRecordsResource.class;
	}

}
