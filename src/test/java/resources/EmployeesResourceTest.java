package resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import transfer.EmployeeTransfer;
import assertion.AssertUtils;
import entity.Gender;

public class EmployeesResourceTest extends ResourceTest {

	@Test
	public void testGetEmployee() {
		Response response = target("employees").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeTransfer transfer = response.readEntity(EmployeeTransfer.class);
		assertEquals(1l, transfer.getId().longValue());
		assertNull(transfer.getPassword());
	}

	@Test
	public void testGetEmployeeWithNotFoundException() {
		Response response = target("employees").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testDeleteEmployee() {
		Response response = target("employees").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteEmployeeWithNotFoundException() {
		Response response = target("employees").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testUpdateEmployee() {
		EmployeeTransfer admin = new EmployeeTransfer();
		admin.setDateOfJoined(new Date());
		admin.setEmail("admin@gmail.com");
		admin.setName("administrator");
		admin.setPassword("secret");
		admin.setUsername("admin");
		admin.setGender(Gender.male.name());
		EmployeeTransfer.Department dep = new EmployeeTransfer.Department();
		dep.setId(2L);
		admin.setDepartment(dep);
		Response response = target("employees").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeTransfer transfer = response.readEntity(EmployeeTransfer.class);
		assertEquals(1l, transfer.getId().longValue());
		assertEquals(admin.getName(), transfer.getName());
		assertEquals(admin.getDateOfJoined(), transfer.getDateOfJoined());
		assertEquals(admin.getEmail(), transfer.getEmail());
		assertEquals(admin.getGender(), transfer.getGender());
		assertEquals(admin.getDepartment().getId(), transfer.getDepartment()
				.getId());
		assertNull(transfer.getPassword());
		assertEquals(admin.getUsername(), transfer.getUsername());
	}

	@Test
	public void testUpdateEmployeeWithNotFoundException() {
		EmployeeTransfer admin = new EmployeeTransfer();
		admin.setDateOfJoined(new Date());
		admin.setEmail("admin@gmail.com");
		admin.setName("administrator");
		admin.setPassword("secret");
		admin.setUsername("admin");
		Response response = target("employees").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(admin));
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testSaveEmployee() {
		EmployeeTransfer admin = new EmployeeTransfer();
		admin.setDateOfJoined(new Date());
		admin.setEmail("admin@gmail.com");
		admin.setName("administrator");
		admin.setPassword("secret");
		admin.setUsername("admin");
		admin.setGender(Gender.male.name());
		EmployeeTransfer.Department dep = new EmployeeTransfer.Department();
		dep.setId(1L);
		admin.setDepartment(dep);
		Response response = target("employees").register(JacksonFeature.class)
				.request().header("user", "demo").post(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeTransfer transfer = response.readEntity(EmployeeTransfer.class);
		assertEquals(3l, transfer.getId().longValue());
		assertEquals(admin.getName(), transfer.getName());
		assertEquals(admin.getDateOfJoined(), transfer.getDateOfJoined());
		assertEquals(admin.getEmail(), transfer.getEmail());
		assertEquals(admin.getGender(), transfer.getGender());
		assertEquals(admin.getDepartment().getId(), transfer.getDepartment()
				.getId());
		assertNull(transfer.getPassword());
		assertEquals(admin.getUsername(), transfer.getUsername());
	}

	@Test
	public void testSaveEmployeeWithDuplicateUsername() {
		EmployeeTransfer admin = new EmployeeTransfer();
		admin.setDateOfJoined(new Date());
		admin.setEmail("admin@gmail.com");
		admin.setName("administrator");
		admin.setPassword("secret");
		admin.setUsername("demo");
		admin.setGender("male");
		EmployeeTransfer.Department dep = new EmployeeTransfer.Department();
		dep.setId(1l);
		admin.setDepartment(dep);
		Response response = target("employees").register(JacksonFeature.class)
				.request().header("user", "demo").post(Entity.json(admin));
		AssertUtils.assertBadRequest(response);
	}

	@Test
	public void testFindallEmployee() {
		Response response = target("employees").register(JacksonFeature.class)
				.request().header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<EmployeeTransfer> rets = response
				.readEntity(new GenericType<PageModel<EmployeeTransfer>>() {
				});
		assertEquals(2, rets.getTotalElements());
	}

	@Override
	Class<?> getResource() {
		return EmployeesResource.class;
	}

}
