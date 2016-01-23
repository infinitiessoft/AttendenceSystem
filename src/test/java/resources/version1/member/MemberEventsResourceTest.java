package resources.version1.member;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import resources.ResourceTest;
import resources.version1.admin.EmployeesResource;
import transfer.EventTransfer;
import assertion.AssertUtils;
import entity.PageModel;

public class MemberEventsResourceTest extends ResourceTest {

	@Test
	public void testFindAll() {
		Response response = target("employees").path("2").path("events")
				.register(JacksonFeature.class).request()
				.header("user", "user").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<EventTransfer> rets = response
				.readEntity(new GenericType<PageModel<EventTransfer>>() {
				});
		assertEquals(1, rets.getTotalElements());
	}

	@Test
	public void testGetEvent() {
		Response response = target("employees").path("2").path("events")
				.path("2").register(JacksonFeature.class).request()
				.header("user", "user").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EventTransfer transfer = response.readEntity(EventTransfer.class);
		assertEquals(2l, transfer.getId().longValue());
	}

	@Test
	public void testGetEventWithNotFoundException() {
		Response response = target("employees").path("2").path("events")
				.path("1").register(JacksonFeature.class).request()
				.header("user", "user").get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testUpdateEvent() {
		EventTransfer admin = new EventTransfer();
		admin.setAction("permit");
		Response response = target("employees").path("2").path("events")
				.path("2").register(JacksonFeature.class).request()
				.header("user", "user").put(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EventTransfer transfer = response.readEntity(EventTransfer.class);
		assertEquals(2l, transfer.getId().longValue());
		assertEquals(admin.getAction(), transfer.getAction());
	}

	@Test
	public void testUpdateEventWithDuplicateApprove() {
		EventTransfer admin = new EventTransfer();
		admin.setAction("permit");
		Response response = target("employees").path("2").path("events")
				.path("2").register(JacksonFeature.class).request()
				.header("user", "user").put(Entity.json(admin));
		response = target("employees").path("2").path("events").path("2")
				.register(JacksonFeature.class).request()
				.header("user", "user").put(Entity.json(admin));
		AssertUtils.assertBadRequest(response);
	}

	@Test
	public void testUpdateEventWithNotFoundException() {
		EventTransfer admin = new EventTransfer();
		admin.setAction("permit");
		Response response = target("employees").path("2").path("events")
				.path("1").register(JacksonFeature.class).request()
				.header("user", "user").put(Entity.json(admin));
		AssertUtils.assertNotFound(response);
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { EmployeesResource.class };
	}

}
