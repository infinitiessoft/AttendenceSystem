package resources.version1.member;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import resources.ResourceTest;
import transfer.RoleTransfer;
import entity.PageModel;

public class MemberRolesResourceTest extends ResourceTest {

	@Test
	public void testFindAllRole() {
		Response response = target("employees").path("2").path("roles")
				.register(JacksonFeature.class).request()
				.header("user", "user").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<RoleTransfer> rets = response
				.readEntity(new GenericType<PageModel<RoleTransfer>>() {
				});
		assertEquals(1, rets.getTotalElements());
	}

	@Test
	public void testFindRole() {
		Response response = target("employees").path("2").path("roles")
				.path("2").register(JacksonFeature.class).request()
				.header("user", "user").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		RoleTransfer transfer = response.readEntity(RoleTransfer.class);
		assertEquals(2l, transfer.getId().longValue());
		assertEquals("user", transfer.getName());
	}

	@Test
	public void testFindRoleWithNotFoundException() {
		Response response = target("employees").path("2").path("roles")
				.path("4").register(JacksonFeature.class).request()
				.header("user", "user").get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { MembersResource.class };
	}

}
