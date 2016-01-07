package resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import entity.AttendRecordType;
import entity.EmployeeLeave;
import transfer.EmployeeLeaveTransfer;
import transfer.LeavesettingTransfer;
import transfer.EmployeeLeaveTransfer.Employee;
import transfer.EmployeeLeaveTransfer.Leavesetting;

public class EmployeeLeaveResourceTest extends ResourceTest {

	@Test
	public void testGetEmployeeLeave() {
		Response response = target("employeeleave").path("1").register(JacksonFeature.class).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeLeaveTransfer transfer = response.readEntity(EmployeeLeaveTransfer.class);
		assertEquals(1L, transfer.getId().longValue());
	}

	@Test
	public void testGetEmployeeLeaveWithNotFoundException() {
		Response response = target("employeeleave").path("2").register(JacksonFeature.class).request().get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testDeleteEmployeeLeave() {
		Response response = target("employeeleave").path("1").register(JacksonFeature.class).request().delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteEmployeeLeaveWithNotFoundException() {
		Response response = target("employeeleave").path("2").register(JacksonFeature.class).request().delete();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testSaveEmployeeLeave() {
		EmployeeLeaveTransfer employeeLeave = new EmployeeLeaveTransfer();
		employeeLeave.setUsedDays(3L);
		Employee employee = new Employee();
		employee.setId(1L);
		employeeLeave.setEmployee(employee);
		Leavesetting leavesetting = new Leavesetting();
		leavesetting.setId(1L);
		employeeLeave.setLeavesetting(leavesetting);

		Response response = target("employeeleave").register(JacksonFeature.class).request()
				.post(Entity.json(employeeLeave));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeLeaveTransfer transfer = response.readEntity(EmployeeLeaveTransfer.class);
		assertEquals(2L, transfer.getId().longValue());
		assertEquals(employeeLeave.getLeavesetting().getId(), leavesetting.getId());
	}

	@Test
	public void testUpdateEmployeeLeave() {
		EmployeeLeave employeeLeave = new EmployeeLeave();
		employeeLeave.setUsedDays(5L);

		entity.Employee employee = new entity.Employee();
		employee.setId(1L);
		employeeLeave.setEmployee(employee);

		entity.Leavesetting leavesetting = new entity.Leavesetting();
		leavesetting.setId(1L);
		employeeLeave.setLeavesetting(leavesetting);
		Response response = target("employeeleave").path("1").register(JacksonFeature.class).request()
				.put(Entity.json(employeeLeave));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeLeaveTransfer transfer = response.readEntity(EmployeeLeaveTransfer.class);
		assertEquals(1L, transfer.getId().longValue());
		assertEquals(employeeLeave.getLeavesetting().getId(), leavesetting.getId());
	}

	@Test
	public void testUpdateEmployeeLeaveWithNotFoundException() {
		EmployeeLeave employeeLeave = new EmployeeLeave();
		employeeLeave.setUsedDays(5L);

		entity.Employee employee = new entity.Employee();
		employee.setId(1L);
		employeeLeave.setEmployee(employee);

		entity.Leavesetting leavesetting = new entity.Leavesetting();
		leavesetting.setId(1L);
		employeeLeave.setLeavesetting(leavesetting);
		Response response = target("employeeleave").path("2").register(JacksonFeature.class).request()
				.put(Entity.json(employeeLeave));
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}
	
	@Test
	public void testFindAllEmployeeLeave() {
		Response response = target("employeeleave").register(JacksonFeature.class).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<EmployeeLeaveTransfer> rets = response
				.readEntity(new GenericType<PageModel<EmployeeLeaveTransfer>>() {
				});
		assertEquals(1, rets.getTotalElements());
		EmployeeLeaveTransfer transfer = rets.getContent().get(0);
		assertEquals(1L, transfer.getId().longValue());
	}

	@Override
	Class<?> getResource() {
		return EmployeeLeaveResource.class;
	}

}
