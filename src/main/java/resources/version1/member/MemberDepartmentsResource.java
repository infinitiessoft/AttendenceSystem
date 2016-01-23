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

import resources.specification.DepartmentSpecification;
import resources.specification.SimplePageRequest;
import service.DepartmentService;
import transfer.DepartmentTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PreAuthorize("isAuthenticated()")
@Path("/departments")
public class MemberDepartmentsResource {

	@Autowired
	private DepartmentService departmentService;

	@GET
	@Path(value = "{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DepartmentTransfer getDepartment(@PathParam("id") long id) {
		return departmentService.retrieve(id);
	}

	// ** Method to find All the departments in the list

	@GET
	public Page<DepartmentTransfer> findallDepartment(
			@BeanParam SimplePageRequest pageRequest,
			@BeanParam DepartmentSpecification spec) {
		return departmentService.findAll(spec, pageRequest);
	}
}
