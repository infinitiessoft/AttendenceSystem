package resources.version1.member;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import resources.specification.EmployeeRoleSpecification;
import resources.specification.SimplePageRequest;
import service.EmployeeRoleService;
import transfer.RoleTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PreAuthorize("isAuthenticated() and #id == principal.id or hasAuthority('admin')")
public class MemberRolesResource {

	@Autowired
	private EmployeeRoleService employeeRoleService;

	@GET
	public Page<RoleTransfer> findAllRole(@PathParam("id") long id,
			@BeanParam EmployeeRoleSpecification spec,
			@BeanParam SimplePageRequest pageRequest) {
		spec.setEmployeeId(id);
		return employeeRoleService.findAll(spec, pageRequest);
	}

	@GET
	@Path(value = "{roleid}")
	public RoleTransfer findRole(@PathParam("id") long id,
			@PathParam("roleid") long roleId) {
		return employeeRoleService.findByEmployeeIdAndRoleId(id, roleId);
	}

}
