package resources.version1.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.ParseException;
import java.util.Calendar;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import resources.ObjectMapperContextResolver;
import resources.ResourceTest;
import transfer.AttendRecordTransfer;
import transfer.Metadata;
import assertion.AssertUtils;
import entity.PageModel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MemberAttendRecordsResourceTest extends ResourceTest {

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
	public void test1GetAttendRecordWithNotFoundException() {
		Response response = target("employees").path("1").path("records")
				.path("4").register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testZDeleteAttendRecord() {
		Response response = target("employees").path("1").path("records")
				.path("1").register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void test1DeleteAttendRecordWithNotFoundException() {
		Response response = target("employees").path("1").path("records")
				.path("4").register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		AssertUtils.assertNotFound(response);
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

		Response response = target("employees").path("1").path("records")
				.path("1").register(JacksonFeature.class)
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
	public void test1UpdateAttendRecordWithModificationNotAllow() {
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

		Response response = target("employees").path("1").path("records")
				.path("2").register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").put(Entity.json(record));
		AssertUtils.assertForbidden(response);
	}

	@Test
	public void test1UpdateAttendRecordWithNotFoundException() {
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

		Response response = target("employees").path("1").path("records")
				.path("3").register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(record));
		AssertUtils.assertNotFound(response);
	}

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
		assertNotNull(transfer.getId());
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
		assertNotNull(transfer.getId());
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
		assertNotNull(transfer.getId());
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
		assertNotNull(transfer.getId());
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
		assertNotNull(transfer.getId());
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

	@Test
	public void testRetrieveMetadataByEmployeeId() {
		Response response = target("employees").path("1").path("records")
				.path("metadata").register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		Metadata rets = response.readEntity(Metadata.class);
		assertEquals(7, rets.size());
		assertEquals(0, rets.get("personal"));
		assertEquals(0, rets.get("annual"));
		assertEquals(0, rets.get("reject"));
		assertEquals(0, rets.get("wedding"));
		assertEquals(1, rets.get("permit"));
		assertEquals(1, rets.get("pending"));
		assertEquals(2, rets.get("sick"));
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { MembersResource.class };
	}

}
