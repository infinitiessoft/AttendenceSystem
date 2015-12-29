package resources;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import entity.Role;
import transfer.RoleTransfer;

public class RolesResourceTest extends ResourceTest {

	@Test
	public void testGetRole() {
		Response response = target("role").path("1").register(JacksonFeature.class).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		RoleTransfer transfer = response.readEntity(RoleTransfer.class);
		assertEquals("1", transfer.getId());
		assertEquals("pohsun", transfer.getName());

	}

	@Test
	public void testGetRoleWithNotFoundException() {
		Response response = target("role").path("2").register(JacksonFeature.class).request().get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testDeleteRole() {
		Response response = target("put").path("1").register(JacksonFeature.class).request().delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteRoleWithNotFoundException() {
		Response response = target("role").path("2").register(JacksonFeature.class).request().delete();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testUpdateRole() {
		Role admin = new Role();
		admin.setName("administrator");
		Response response = target("role").path("1").register(JacksonFeature.class).request().put(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		RoleTransfer transfer = response.readEntity(RoleTransfer.class);
		assertEquals("1", transfer.getId());
		assertEquals(admin.getName(), transfer.getName());
	}

	@Test
	public void testUpdateRoleWithNotFoundException() {
		Role admin = new Role();
		admin.setName("administrator");
		Response response = target("role").path("2").register(JacksonFeature.class).request().put(Entity.json(admin));
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testSaveRole() {
		Role admin = new Role();
		admin.setName("administrator");
		Response response = target("role").register(JacksonFeature.class).request().post(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		RoleTransfer transfer = response.readEntity(RoleTransfer.class);
		assertEquals("2", transfer.getId());
		assertEquals("Poshun", transfer.getName());
	}

	@Test
	public void testSaveRoleWithDuplicateName() {
		Role admin = new Role();
		admin.setName("administrator");
		Response response = target("role").register(JacksonFeature.class).request().post(Entity.json(admin));
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void testFindallRole() {
		Response response = target("role").register(JacksonFeature.class).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		Collection<RoleTransfer> rets = response.readEntity(new GenericType<Collection<RoleTransfer>>() {
		});
		assertEquals(1, rets.size());
		RoleTransfer transfer = rets.iterator().next();
		assertEquals("1", transfer.getId());
		assertEquals("pohsun", transfer.getName());

	}

	@Override
	Class<?> getResource() {
		return RolesResource.class;
	}

}
