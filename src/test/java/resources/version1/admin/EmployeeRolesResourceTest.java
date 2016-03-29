package resources.version1.admin;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import resources.ResourceTest;
import transfer.RoleTransfer;
import assertion.AssertUtils;
import entity.PageModel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeRolesResourceTest extends ResourceTest {

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
	public void test1FindRoleWithNotFoundException() {
		Response response = target("employees").path("2").path("roles")
				.path("4").register(JacksonFeature.class).request()
				.header("user", "user").get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testZAssignRoleToEmployee() {
		Response response = target("employees")
				.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION,
						true).path("2").path("roles").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "user").put(null);
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void test1AssignRoleToEmployeeWithRoleNotFound() {
		Response response = target("employees")
				.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION,
						true).path("2").path("roles").path("4")
				.register(JacksonFeature.class).request()
				.header("user", "user").put(null);
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testRevokeRoleToEmployee() {
		Response response = target("employees")
				.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION,
						true).path("2").path("roles").path("2")
				.register(JacksonFeature.class).request()
				.header("user", "user").delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void test1RevokeRoleToEmployeeWithRoleNotFound() {
		Response response = target("employees")
				.property(ClientProperties.SUPPRESS_HTTP_COMPLIANCE_VALIDATION,
						true).path("2").path("roles").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "user").delete();
		AssertUtils.assertNotFound(response);
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { EmployeesResource.class };
	}

}
