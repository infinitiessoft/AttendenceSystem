package resources;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import transfer.EmployeeTransfer;
import entity.Employee;

public class EmployeesResourceTest extends ResourceTest {

	@Test
	public void testGetEmployee() {
		Response response = target("employee").path("1")
				.register(JacksonFeature.class).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeTransfer transfer = response.readEntity(EmployeeTransfer.class);
		assertEquals(1l, transfer.getId());
		assertEquals("pohsun", transfer.getName());
		assertEquals(2, transfer.getRoles().size());
		assertEquals(true, transfer.getRoles().get("admin"));
		assertEquals(true, transfer.getRoles().get("user"));
	}

	@Test
	public void testGetEmployeeWithNotFoundException() {
		Response response = target("employee").path("2")
				.register(JacksonFeature.class).request().get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testDeleteEmployee() {
		Response response = target("employee").path("1")
				.register(JacksonFeature.class).request().delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteEmployeeWithNotFoundException() {
		Response response = target("employee").path("2")
				.register(JacksonFeature.class).request().delete();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testUpdateEmployee() {
		Employee admin = new Employee();
		admin.setDateofbirth(new Date());
		admin.setDateofjoined(new Date());
		admin.setEmail("admin@gmail.com");
		admin.setName("administrator");
		admin.setPassword("secret");
		admin.setUsername("admin");
		Response response = target("employee").path("1")
				.register(JacksonFeature.class).request()
				.put(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeTransfer transfer = response.readEntity(EmployeeTransfer.class);
		assertEquals(2l, transfer.getId());
		assertEquals(admin.getUsername(), transfer.getName());
		assertEquals(0, transfer.getRoles().size());
	}

	@Test
	public void testUpdateEmployeeWithNotFoundException() {
		Employee admin = new Employee();
		admin.setDateofbirth(new Date());
		admin.setDateofjoined(new Date());
		admin.setEmail("admin@gmail.com");
		admin.setName("administrator");
		admin.setPassword("secret");
		admin.setUsername("admin");
		Response response = target("employee").path("2")
				.register(JacksonFeature.class).request()
				.put(Entity.json(admin));
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testSaveEmployee() {
		Employee admin = new Employee();
		admin.setDateofbirth(new Date());
		admin.setDateofjoined(new Date());
		admin.setEmail("admin@gmail.com");
		admin.setName("administrator");
		admin.setPassword("secret");
		admin.setUsername("admin");
		Response response = target("employee").register(JacksonFeature.class)
				.request().post(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeTransfer transfer = response.readEntity(EmployeeTransfer.class);
		assertEquals(2l, transfer.getId());
		assertEquals(admin.getUsername(), transfer.getName());
		assertEquals(0, transfer.getRoles().size());
	}

	@Test
	public void testSaveEmployeeWithDuplicateName() {
		Employee admin = new Employee();
		admin.setDateofbirth(new Date());
		admin.setDateofjoined(new Date());
		admin.setEmail("admin@gmail.com");
		admin.setName("administrator");
		admin.setPassword("secret");
		admin.setUsername("pohsun");
		Response response = target("employee").register(JacksonFeature.class)
				.request().post(Entity.json(admin));
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void testFindallEmployee() {
		Response response = target("employee").register(JacksonFeature.class)
				.request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		Collection<EmployeeTransfer> rets = response
				.readEntity(new GenericType<Collection<EmployeeTransfer>>() {
				});
		assertEquals(1, rets.size());
		EmployeeTransfer transfer = rets.iterator().next();
		assertEquals(1l, transfer.getId());
		assertEquals("pohsun", transfer.getName());
		assertEquals(2, transfer.getRoles().size());
		assertEquals(true, transfer.getRoles().get("admin"));
		assertEquals(true, transfer.getRoles().get("user"));
	}

	@Override
	Class<?> getResource() {
		return EmployeesResource.class;
	}

}
