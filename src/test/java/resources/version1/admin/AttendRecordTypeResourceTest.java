package resources.version1.admin;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import resources.ResourceTest;
import transfer.AttendRecordTypeTransfer;
import assertion.AssertUtils;
import entity.PageModel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AttendRecordTypeResourceTest extends ResourceTest {

	@Test
	public void testGetAttendRecordType() {
		Response response = target("recordtypes").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		AttendRecordTypeTransfer transfer = response
				.readEntity(AttendRecordTypeTransfer.class);
		assertEquals(1l, transfer.getId().longValue());
		assertEquals("sick", transfer.getName());
	}

	@Test
	public void test1GetAttendRecordTypeWithNotFoundException() {
		Response response = target("recordtypes").path("5")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testZDeleteAttendRecordType() {
		Response response = target("recordtypes").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void test1ZDeleteAttendRecordTypeWithNotFoundException() {
		Response response = target("recordtypes").path("5")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testUpdateAttendRecordType() {
		AttendRecordTypeTransfer admin = new AttendRecordTypeTransfer();
		admin.setName("official");
		Response response = target("recordtypes").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		AttendRecordTypeTransfer transfer = response
				.readEntity(AttendRecordTypeTransfer.class);
		assertEquals(1l, transfer.getId().longValue());
		assertEquals(admin.getName(), transfer.getName());
	}

	@Test
	public void testUpdateAttendRecordTypeWithNotFoundException() {
		AttendRecordTypeTransfer admin = new AttendRecordTypeTransfer();
		admin.setName("official");
		Response response = target("recordtypes").path("5")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(admin));
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testSaveAttendRecordType() {
		AttendRecordTypeTransfer admin = new AttendRecordTypeTransfer();
		admin.setName("testing");
		Response response = target("recordtypes")
				.register(JacksonFeature.class).request()
				.header("user", "demo").post(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		AttendRecordTypeTransfer transfer = response
				.readEntity(AttendRecordTypeTransfer.class);
		assertEquals(6l, transfer.getId().longValue());
		assertEquals(admin.getName(), transfer.getName());
	}

	@Test
	public void test1SaveAttendRecordTypeWithDuplicateName() {
		AttendRecordTypeTransfer admin = new AttendRecordTypeTransfer();
		admin.setName("sick");
		Response response = target("recordtypes")
				.register(JacksonFeature.class).request()
				.header("user", "demo").post(Entity.json(admin));
		AssertUtils.assertBadRequest(response);
	}

	@Test
	public void testFindallAttendRecordType() {
		Response response = target("recordtypes")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<AttendRecordTypeTransfer> rets = response
				.readEntity(new GenericType<PageModel<AttendRecordTypeTransfer>>() {
				});
		assertEquals(4, rets.getTotalElements());
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { AttendRecordTypesResource.class };
	}

}
