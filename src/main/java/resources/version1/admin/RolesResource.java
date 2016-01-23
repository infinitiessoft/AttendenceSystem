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

import resources.specification.RoleSpecification;
import resources.specification.SimplePageRequest;
import service.RoleService;
import transfer.RoleTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/roles")
public class RolesResource {

	@Autowired
	private RoleService roleService;

	@GET
	@Path(value = "{id}")
	public RoleTransfer getRole(@PathParam("id") long id) {
		return roleService.retrieve(id);
	}

	// ** Method to delete
	@DELETE
	@Path(value = "{id}")
	public Response deleteRole(@PathParam("id") long id) {
		roleService.delete(id);
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON)
				.build();
	}

	// **Method to update
	@PUT
	@Path(value = "{id}")
	public RoleTransfer updateRole(@PathParam("id") long id, RoleTransfer role) {
		return roleService.update(id, role);
	}

	// **Method to save or create
	@POST
	public RoleTransfer saveRole(RoleTransfer role) {
		return roleService.save(role);
	}

	// ** Method to find All the roles in the list

	@GET
	public Page<RoleTransfer> findallRole(
			@BeanParam SimplePageRequest pageRequest,
			@BeanParam RoleSpecification spec) {
		return roleService.findAll(spec, pageRequest);
	}

}
