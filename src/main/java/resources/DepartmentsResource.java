package resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import resources.specification.DepartmentSpecification;
import service.DepartmentService;
import transfer.DepartmentTransfer;
import entity.Department;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/department")
public class DepartmentsResource {

	@Autowired
	private DepartmentService departmentService;

	@GET
	@Path(value = "{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DepartmentTransfer getDepartment(@PathParam("id") long id) {
		return departmentService.retrieve(id);
	}

	// ** Method to delete
	@DELETE
	@Path(value = "{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteDepartment(@PathParam("id") long id) {
		departmentService.delete(id);
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON)
				.build();
	}

	// **Method to update
	@PUT
	@Path(value = "{id}")
	public DepartmentTransfer updateDepartment(@PathParam("id") long id,
			DepartmentTransfer department) {
		return departmentService.update(id, department);
	}

	// **Method to save or create
	@POST
	public DepartmentTransfer saveDepartment(DepartmentTransfer department) {
		return departmentService.save(department);
	}

	// ** Method to find All the departments in the list

	@GET
	public Page<DepartmentTransfer> findallDepartment(
			@QueryParam("page") @DefaultValue("0") Integer page,
			@QueryParam("pageSize") @DefaultValue("20") Integer pageSize,
			@QueryParam("sort") @DefaultValue("id") String sort,
			@QueryParam("dir") @DefaultValue("ASC") String dir,
			@BeanParam DepartmentSpecification spec) {
		return departmentService.findAll(spec, page, pageSize, sort, dir);
	}
}
