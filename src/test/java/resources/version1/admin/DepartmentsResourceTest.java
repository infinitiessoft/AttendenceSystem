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
import transfer.DepartmentTransfer;
import assertion.AssertUtils;
import entity.PageModel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentsResourceTest extends ResourceTest {

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
	public void test1GetDepartmentWithNotFoundException() {
		Response response = target("departments").path("4")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
	}

	@Test
	public void testZDeleteleteDepartment() {
		Response response = target("departments").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void test1DeleteDepartmentWithNotFoundException() {
		Response response = target("departments").path("4")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testUpdateDepartment() {
		DepartmentTransfer admin = new DepartmentTransfer();
		admin.setName("administrator");
		Response response = target("departments").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		DepartmentTransfer transfer = response
				.readEntity(DepartmentTransfer.class);
		assertEquals(1l, transfer.getId().longValue());
		assertEquals(admin.getName(), transfer.getName());
	}

	@Test
	public void test1UpdateDepartmentWithNotFoundException() {
		DepartmentTransfer admin = new DepartmentTransfer();
		admin.setName("administrator");
		Response response = target("departments").path("4")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(admin));
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testSaveDepartment() {
		DepartmentTransfer admin = new DepartmentTransfer();
		admin.setName("testing");
		Response response = target("departments")
				.register(JacksonFeature.class).request()
				.header("user", "demo").post(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		DepartmentTransfer transfer = response
				.readEntity(DepartmentTransfer.class);
		assertEquals(5l, transfer.getId().longValue());
		assertEquals(admin.getName(), transfer.getName());
	}

	@Test
	public void test1SaveDepartmentWithDuplicateName() {
		DepartmentTransfer admin = new DepartmentTransfer();
		admin.setName("Sales");
		Response response = target("departments")
				.register(JacksonFeature.class).request()
				.header("user", "demo").post(Entity.json(admin));
		AssertUtils.assertBadRequest(response);
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
		return new Class<?>[] { DepartmentsResource.class };
	}

}
