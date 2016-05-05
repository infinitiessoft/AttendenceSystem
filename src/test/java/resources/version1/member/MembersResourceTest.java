package resources.version1.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import resources.ResourceTest;
import transfer.EmployeeLeaveTransfer;
import transfer.EmployeeTransfer;
import entity.Gender;
import entity.PageModel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
		assertEquals(1l, transfer.getDepartment().getId().longValue());
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

	@Test
	public void testFindEmployeeLeave() {
		Response response = target("employees").path("1")
				.path("employeeLeaves").register(JacksonFeature.class)
				.queryParam("typeName", "sick").request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeLeaveTransfer transfer = response
				.readEntity(EmployeeLeaveTransfer.class);
		// assertEquals(3l, transfer.getId().longValue());
		assertEquals(1, transfer.getEmployee().getId().intValue());
		assertEquals("demo", transfer.getEmployee().getName());
		assertEquals(0d, transfer.getUsedDays().doubleValue(), 0.1);
		assertEquals(3, transfer.getLeavesetting().getId().intValue());
		assertEquals("sick_3_10", transfer.getLeavesetting().getName());
		assertEquals(10d, transfer.getLeavesetting().getDays().doubleValue(),
				0.1);
		assertEquals(3l, transfer.getLeavesetting().getYear().longValue());
		assertEquals(1l, transfer.getLeavesetting().getType().getId()
				.longValue());
		assertEquals("sick", transfer.getLeavesetting().getType().getName());
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { MembersResource.class };
	}

}
