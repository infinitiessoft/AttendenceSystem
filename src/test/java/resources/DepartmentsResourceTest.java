package resources;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import entity.Department;
import transfer.DepartmentTransfer;

public class DepartmentsResourceTest extends ResourceTest {

	@Test
	public void testGetDepartment() {
		Response response = target("department").path("1").register(JacksonFeature.class).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		DepartmentTransfer transfer = response.readEntity(DepartmentTransfer.class);
		assertEquals(1l, transfer.getId());
		assertEquals("pohsun", transfer.getName());

	}

	@Test
	public void testGetDepartmentWithNotFoundException() {
		Response response = target("department").path("2").register(JacksonFeature.class).request().get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testDeleteDepartment() {
		Response response = target("department").path("1").register(JacksonFeature.class).request().delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteDepartmentWithNotFoundException() {
		Response response = target("department").path("2").register(JacksonFeature.class).request().delete();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testUpdateDepartment() {
		Department admin = new Department();
		admin.setName("administrator");
		Response response = target("department").path("1").register(JacksonFeature.class).request()
				.put(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		DepartmentTransfer transfer = response.readEntity(DepartmentTransfer.class);
		assertEquals(1l, transfer.getId());
		assertEquals(admin.getName(), transfer.getName());
		assertEquals("1", transfer.getManager_id());
		assertEquals("2", transfer.getResponseto());
	}

	@Test
	public void testUpdateDepartmentWithNotFoundException() {
		Department admin = new Department();
		admin.setName("administrator");
		Response response = target("department").path("2").register(JacksonFeature.class).request()
				.put(Entity.json(admin));
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testSaveDepartment() {
		Department admin = new Department();
		admin.setName("administrator");
		Response response = target("department").register(JacksonFeature.class).request().post(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		DepartmentTransfer transfer = response.readEntity(DepartmentTransfer.class);
		assertEquals(2l, transfer.getId());
		assertEquals(admin.getName(), transfer.getName());
		assertEquals("1", transfer.getManager_id());
		assertEquals("2", transfer.getResponseto());
	}

	@Test
	public void testSaveDepartmentWithDuplicateName() {
		Department admin = new Department();
		admin.setName("administrator");
		Response response = target("department").register(JacksonFeature.class).request().post(Entity.json(admin));
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void testFindallDepartment() {
		Response response = target("department").register(JacksonFeature.class).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		Collection<DepartmentTransfer> rets = response.readEntity(new GenericType<Collection<DepartmentTransfer>>() {
		});
		assertEquals(1, rets.size());
		DepartmentTransfer transfer = rets.iterator().next();
		assertEquals(1l, transfer.getId());
		assertEquals("pohsun", transfer.getName());
		assertEquals("1", transfer.getManager_id());
		assertEquals("2", transfer.getResponseto());
	}

	@Override
	Class<?> getResource() {
		return DepartmentsResource.class;
	}

}
