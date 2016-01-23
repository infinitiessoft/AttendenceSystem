package resources.version1.admin;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import resources.specification.EmployeeSpecification;
import resources.specification.SimplePageRequest;
import resources.version1.member.MemberAttendRecordsResource;
import resources.version1.member.MemberEventsResource;
import service.EmployeeService;
import transfer.EmployeeTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/employees")
public class EmployeesResource {

	@Autowired
	private EmployeeService employeeService;

	@GET
	@Path(value = "{id}")
	public EmployeeTransfer getEmployee(@PathParam("id") long id) {
		return employeeService.retrieve(id);
	}

	// ** Method to delete
	@DELETE
	@Path(value = "{id}")
	public Response deleteEmployee(@PathParam("id") long id) {
		employeeService.delete(id);
		return Response.status(Status.OK)
				.entity("employee has been successfully deleted")
				.type(MediaType.APPLICATION_JSON).build();
	}

	// **Method to update
	@PUT
	@Path(value = "{id}")
	public EmployeeTransfer updateEmployee(@PathParam("id") long id,
			EmployeeTransfer employee) {
		return employeeService.update(id, employee);
	}

	// **Method to save or create
	@POST
	public EmployeeTransfer saveEmployee(EmployeeTransfer employee) {
		return employeeService.save(employee);
	}

	// ** Method to find All the employees in the list

	@GET
	public Page<EmployeeTransfer> findAllEmployee(
			@BeanParam SimplePageRequest pageRequest,
			@BeanParam EmployeeSpecification spec) {
		return employeeService.findAll(spec, pageRequest);
	}

	@Path("{id}/roles")
	public Class<EmployeeRolesResource> getEmployeeRolesResource() {
		return EmployeeRolesResource.class;
	}

	@Path("{id}/records")
	public Class<MemberAttendRecordsResource> getEmployeeAttendRecordsResource() {
		return MemberAttendRecordsResource.class;
	}

	@Path("{id}/events")
	public Class<MemberEventsResource> getEmployeeEventsResource() {
		return MemberEventsResource.class;
	}

}
