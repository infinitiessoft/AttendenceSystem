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
import transfer.EventTransfer;
import assertion.AssertUtils;
import entity.PageModel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EventsResourceTest extends ResourceTest {

	@Test
	public void testGetEvent() {
		Response response = target("events").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EventTransfer transfer = response.readEntity(EventTransfer.class);
		assertEquals(1l, transfer.getId().longValue());
	}

	@Test
	public void test1GetEventWithNotFoundException() {
		Response response = target("events").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testUpdateEvent() {
		EventTransfer admin = new EventTransfer();
		admin.setAction("permit");
		Response response = target("events").path("2")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EventTransfer transfer = response.readEntity(EventTransfer.class);
		assertEquals(2l, transfer.getId().longValue());
		assertEquals(admin.getAction(), transfer.getAction());
	}

	@Test
	public void test1UpdateEventWithDuplicateApprove() {
		EventTransfer admin = new EventTransfer();
		admin.setAction("permit");
		Response response = target("events").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(admin));
		AssertUtils.assertBadRequest(response);
	}

	@Test
	public void test1UpdateEventWithNotFoundException() {
		EventTransfer admin = new EventTransfer();
		admin.setAction("permit");
		Response response = target("events").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(admin));
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testFindallEvent() {
		Response response = target("events").register(JacksonFeature.class)
				.request().header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<EventTransfer> rets = response
				.readEntity(new GenericType<PageModel<EventTransfer>>() {
				});
		assertEquals(2, rets.getTotalElements());
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { EventsResource.class };
	}

}
