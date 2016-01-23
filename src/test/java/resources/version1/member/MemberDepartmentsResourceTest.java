package resources.version1.member;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import resources.ResourceTest;
import transfer.DepartmentTransfer;
import entity.PageModel;

public class MemberDepartmentsResourceTest extends ResourceTest {

	@Test
	public void testGetDepartment() {
		Response response = target("departments").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		DepartmentTransfer transfer = response
				.readEntity(DepartmentTransfer.class);
		assertEquals(1l, transfer.getId().longValue());
		assertEquals("Sales", transfer.getName());
	}

	@Test
	public void testGetDepartmentWithNotFoundException() {
		Response response = target("departments").path("4")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testFindallDepartment() {
		Response response = target("departments")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<DepartmentTransfer> rets = response
				.readEntity(new GenericType<PageModel<DepartmentTransfer>>() {
				});
		assertEquals(3, rets.getTotalElements());
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { MemberDepartmentsResource.class };
	}

}
