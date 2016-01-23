package resources.version1.admin;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import resources.ResourceTest;
import transfer.RoleTransfer;
import assertion.AssertUtils;
import entity.PageModel;

public class RolesResourceTest extends ResourceTest {

	@Test
	public void testGetRole() {
		Response response = target("roles").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		RoleTransfer transfer = response.readEntity(RoleTransfer.class);
		assertEquals(1l, transfer.getId().longValue());
		assertEquals("admin", transfer.getName());

	}

	@Test
	public void testGetRoleWithNotFoundException() {
		Response response = target("roles").path("4")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteRole() {
		Response response = target("roles").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteRoleWithNotFoundException() {
		Response response = target("roles").path("4")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testUpdateRole() {
		RoleTransfer admin = new RoleTransfer();
		admin.setName("administrator");
		Response response = target("roles").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		RoleTransfer transfer = response.readEntity(RoleTransfer.class);
		assertEquals(1l, transfer.getId().longValue());
		assertEquals(admin.getName(), transfer.getName());
	}

	@Test
	public void testUpdateRoleWithNotFoundException() {
		RoleTransfer admin = new RoleTransfer();
		admin.setName("administrator");
		Response response = target("roles").path("4")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(admin));
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testSaveRole() {
		RoleTransfer newEntry = new RoleTransfer();
		newEntry.setName("new");
		Response response = target("roles").register(JacksonFeature.class)
				.request().header("user", "demo").post(Entity.json(newEntry));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		RoleTransfer transfer = response.readEntity(RoleTransfer.class);
		assertEquals(4l, transfer.getId().longValue());
		assertEquals(newEntry.getName(), transfer.getName());
	}

	@Test
	public void testSaveRoleWithDuplicateName() {
		RoleTransfer admin = new RoleTransfer();
		admin.setName("admin");
		Response response = target("roles").register(JacksonFeature.class)
				.request().header("user", "demo").post(Entity.json(admin));
		AssertUtils.assertBadRequest(response);
	}

	@Test
	public void testFindallRole() {
		Response response = target("roles").register(JacksonFeature.class)
				.request().header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<RoleTransfer> rets = response
				.readEntity(new GenericType<PageModel<RoleTransfer>>() {
				});
		assertEquals(3, rets.getTotalElements());
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { RolesResource.class };
	}

}
