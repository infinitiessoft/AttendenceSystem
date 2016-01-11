package resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
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

import resources.specification.EmployeeRoleSpecification;
import resources.specification.SimplePageRequest;
import service.EmployeeRoleService;
import transfer.RoleTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeRolesResource {

	@Autowired
	private EmployeeRoleService employeeRoleService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<RoleTransfer> findAllRole(@PathParam("id") long id,
			@BeanParam SimplePageRequest pageRequest) {
		EmployeeRoleSpecification spec = new EmployeeRoleSpecification();
		spec.setEmployeeId(id);
		return employeeRoleService.findAll(spec, pageRequest);
	}

	@GET
	@Path(value = "{roleid}")
	@Produces(MediaType.APPLICATION_JSON)
	public RoleTransfer findRole(@PathParam("id") long employeeId,
			@PathParam("roleid") long roleId) {
		return employeeRoleService
				.findByEmployeeIdAndRoleId(employeeId, roleId);
	}

	@PUT
	@Path(value = "{roleid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignRoleToEmployee(
			@PathParam("id") long employeeId,
			@PathParam("roleid") long roleId) {
		employeeRoleService.assignRoleToEmployee(employeeId, roleId);
		return Response.status(Status.NO_CONTENT)
				.type(MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	@Path(value = "{roleid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response revokeRoleToEmployee(
			@PathParam("id") long employeeId,
			@PathParam("roleid") long roleId) {
		employeeRoleService.delete(employeeId, roleId);
		return Response.status(Status.NO_CONTENT)
				.type(MediaType.APPLICATION_JSON).build();
	}

}
