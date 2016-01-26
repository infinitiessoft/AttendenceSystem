package resources.version1.admin;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import resources.ResourceTest;
import transfer.EmployeeLeaveTransfer;
import transfer.EmployeeLeaveTransfer.Employee;
import transfer.EmployeeLeaveTransfer.Leavesetting;
import assertion.AssertUtils;
import entity.PageModel;

public class EmployeeLeaveResourceTest extends ResourceTest {

	@Test
	public void testGetEmployeeLeave() {
		Response response = target("employeeleaves").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeLeaveTransfer transfer = response
				.readEntity(EmployeeLeaveTransfer.class);
		assertEquals(1l, transfer.getId().longValue());
	}

	@Test
	public void testGetEmployeeLeaveWithNotFoundException() {
		Response response = target("employeeleaves").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testDeleteEmployeeLeave() {
		Response response = target("employeeleaves").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteEmployeeLeaveWithNotFoundException() {
		Response response = target("employeeleaves").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testSaveEmployeeLeave() {
		EmployeeLeaveTransfer employeeLeave = new EmployeeLeaveTransfer();
		employeeLeave.setUsedDays(3d);
		Employee employee = new Employee();
		employee.setId(2L);
		employeeLeave.setEmployee(employee);
		Leavesetting leavesetting = new Leavesetting();
		leavesetting.setId(1L);
		employeeLeave.setLeavesetting(leavesetting);

		Response response = target("employeeleaves")
				.register(JacksonFeature.class).request()
				.header("user", "demo").post(Entity.json(employeeLeave));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeLeaveTransfer transfer = response
				.readEntity(EmployeeLeaveTransfer.class);
		assertEquals(4L, transfer.getId().longValue());
		assertEquals(employeeLeave.getLeavesetting().getId(), transfer
				.getLeavesetting().getId());
		assertEquals(employeeLeave.getEmployee().getId(), transfer
				.getEmployee().getId());
		assertEquals(employeeLeave.getUsedDays(), transfer.getUsedDays());
	}

	@Test
	public void testSaveEmployeeLeaveWithDuplicateLeavesettingAndEmployee() {
		EmployeeLeaveTransfer employeeLeave = new EmployeeLeaveTransfer();
		employeeLeave.setUsedDays(3d);
		Employee employee = new Employee();
		employee.setId(1L);
		employeeLeave.setEmployee(employee);
		Leavesetting leavesetting = new Leavesetting();
		leavesetting.setId(1L);
		employeeLeave.setLeavesetting(leavesetting);
		Response response = target("employeeleaves")
				.register(JacksonFeature.class).request()
				.header("user", "demo").post(Entity.json(employeeLeave));
		AssertUtils.assertBadRequest(response);
	}

	@Test
	public void testUpdateEmployeeLeave() {
		EmployeeLeaveTransfer employeeLeave = new EmployeeLeaveTransfer();
		employeeLeave.setUsedDays(5d);

		Response response = target("employeeleaves").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(employeeLeave));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeLeaveTransfer transfer = response
				.readEntity(EmployeeLeaveTransfer.class);
		assertEquals(1L, transfer.getId().longValue());
		assertEquals(employeeLeave.getUsedDays(), employeeLeave.getUsedDays());
	}

	@Test
	public void testUpdateEmployeeLeaveWithNotFoundException() {
		EmployeeLeaveTransfer employeeLeave = new EmployeeLeaveTransfer();
		employeeLeave.setUsedDays(5d);

		EmployeeLeaveTransfer.Employee employee = new EmployeeLeaveTransfer.Employee();
		employee.setId(1L);
		employeeLeave.setEmployee(employee);

		EmployeeLeaveTransfer.Leavesetting leavesetting = new EmployeeLeaveTransfer.Leavesetting();
		leavesetting.setId(1L);
		employeeLeave.setLeavesetting(leavesetting);
		Response response = target("employeeleaves").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(employeeLeave));
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testFindAllEmployeeLeave() {
		Response response = target("employeeleaves")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<EmployeeLeaveTransfer> rets = response
				.readEntity(new GenericType<PageModel<EmployeeLeaveTransfer>>() {
				});
		assertEquals(2, rets.getTotalElements());
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { EmployeeLeavesResource.class };
	}

}
