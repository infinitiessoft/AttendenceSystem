package resources.version1.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import resources.ResourceTest;
import transfer.EmployeeTransfer;
import entity.Gender;
import entity.PageModel;

public class MembersResourceTest extends ResourceTest {

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
		assertEquals(1l, transfer.getDepartment()
				.getId().longValue());
		assertNull(transfer.getPassword());
		assertEquals(admin.getUsername(), transfer.getUsername());
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
	protected Class<?>[] getResource() {
		return new Class<?>[] { MembersResource.class };
	}

}
