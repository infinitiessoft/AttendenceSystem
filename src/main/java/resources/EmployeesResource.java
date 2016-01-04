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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

import resources.specification.EmployeeRoleSpecification;
import resources.specification.EmployeeSpecification;
import resources.specification.SimplePageRequest;
import service.EmployeeRoleService;
import service.EmployeeService;
import transfer.EmployeeTransfer;
import transfer.RoleTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/employee")
public class EmployeesResource {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeRoleService employeeRoleService;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authManager;

	@GET
	@Path(value = "{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public EmployeeTransfer getEmployee(@PathParam("id") long id) {
		return employeeService.retrieve(id);
	}

	// ** Method to delete
	@DELETE
	@Path(value = "{id}")
	@Consumes(MediaType.APPLICATION_JSON)
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

	@GET
	@Path(value = "{employeeid}/roles/")
	@Produces(MediaType.APPLICATION_JSON)
	public Page<RoleTransfer> findAllRole(@PathParam("employeeid") long id,
			@BeanParam SimplePageRequest pageRequest) {
		EmployeeRoleSpecification spec = new EmployeeRoleSpecification();
		spec.setEmployeeId(id);
		return employeeRoleService.findAll(spec, pageRequest);
	}

	@GET
	@Path(value = "{employeeid}/roles/{roleid}")
	@Produces(MediaType.APPLICATION_JSON)
	public RoleTransfer findRole(@PathParam("employeeid") long employeeId,
			@PathParam("roleid") long roleId) {
		return employeeRoleService
				.findByEmployeeIdAndRoleId(employeeId, roleId);
	}

	@PUT
	@Path(value = "{employeeid}/roles/{roleid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignRoleToEmployee(
			@PathParam("employeeid") long employeeId,
			@PathParam("roleid") long roleId) {
		employeeRoleService.assignRoleToEmployee(employeeId, roleId);
		return Response.status(Status.NO_CONTENT)
				.type(MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path(value = "{employeeid}/roles/{roleid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response revokeRoleToEmployee(
			@PathParam("employeeid") long employeeId,
			@PathParam("roleid") long roleId) {
		employeeRoleService.delete(employeeId, roleId);
		return Response.status(Status.NO_CONTENT)
				.type(MediaType.APPLICATION_JSON).build();
	}

}
