package resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import entity.Employee;
import entity.EmployeeLeave;
import entity.Leavesetting;
import transfer.EmployeeLeaveTransfer;

public class EmployeeLeaveResourceTest extends ResourceTest{

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
		EmployeeLeave employeeLeave = new EmployeeLeave();
		employeeLeave.setUsedDays(3L);
		
		Employee employee = new Employee();
		employee.setId(1L);
		employeeLeave.setEmployee(employee);
		
		Leavesetting leavesetting = new Leavesetting();
		leavesetting.setId(1L);
		employeeLeave.setLeavesetting(leavesetting);
		
		Response response = target("employeeleave").register(JacksonFeature.class).request().post(Entity.json(employeeLeave));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		EmployeeLeaveTransfer transfer = response.readEntity(EmployeeLeaveTransfer.class);
		assertEquals(2L, transfer.getId().longValue());
		assertEquals(employeeLeave.getLeavesetting().getId(), leavesetting.getId());
	}
	
	@Override
	Class<?> getResource() {
		return EmployeeLeaveResource.class;
	}

}
