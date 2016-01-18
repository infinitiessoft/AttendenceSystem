package resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import resources.specification.EmployeeSpecification;
import resources.specification.SimplePageRequest;
import service.EmployeeService;
import transfer.EmployeeTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/employee")
public class EmployeesResource {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;

	@GET
	@Path(value = "{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@PreAuthorize("hasRole('admin') or #id == principal.id")
	public EmployeeTransfer getEmployee(@PathParam("id") long id,
			@Context SecurityContext sc) {
		return employeeService.retrieve(id);
	}

	// ** Method to delete
	@DELETE
	@Path(value = "{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@PreAuthorize("hasRole('admin') or #id == principal.id")
	public Response deleteEmployee(@PathParam("id") long id) {
		employeeService.delete(id);
		return Response.status(Status.OK)
				.entity("employee has been successfully deleted")
				.type(MediaType.APPLICATION_JSON).build();
	}

	// **Method to update
	@PUT
	@Path(value = "{id}")
	@PreAuthorize("hasRole('admin') or #id == principal.id")
	public EmployeeTransfer updateEmployee(@PathParam("id") long id,
			EmployeeTransfer employee) {
		return employeeService.update(id, employee);
	}

	// **Method to save or create
	@POST
	@PreAuthorize("hasRole('admin')")
	public EmployeeTransfer saveEmployee(EmployeeTransfer employee) {
		return employeeService.save(employee);
	}

	// ** Method to find All the employees in the list

	@GET
	@PreAuthorize("hasRole('admin')")
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
	public Class<EmployeeAttendRecordsResource> getEmployeeAttendRecordsResource() {
		return EmployeeAttendRecordsResource.class;
	}

	@Path("{id}/events")
	public Class<EmployeeEventsResource> getEmployeeEventsResource() {
		return EmployeeEventsResource.class;
	}

}
