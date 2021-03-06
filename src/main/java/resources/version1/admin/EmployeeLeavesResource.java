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

import resources.specification.EmployeeLeaveSpecification;
import resources.specification.SimplePageRequest;
import service.EmployeeLeaveService;
import transfer.EmployeeLeaveTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/employeeleaves")
public class EmployeeLeavesResource {

	@Autowired
	private EmployeeLeaveService employeeLeaveService;

	@GET
	@Path(value = "{id}")
	public EmployeeLeaveTransfer getEmployeeLeave(@PathParam("id") long id) {
		return employeeLeaveService.retrieve(id);
	}

	@DELETE
	@Path(value = "{id}")
	public Response deleteEmployeeLeave(@PathParam("id") long id) {
		employeeLeaveService.delete(id);
		return Response.status(Status.NO_CONTENT)
				.type(MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path(value = "{id}")
	public EmployeeLeaveTransfer updateEmployeeLeave(@PathParam("id") long id,
			EmployeeLeaveTransfer transfer) {
		return employeeLeaveService.update(id, transfer);
	}

	@POST
	public EmployeeLeaveTransfer saveEmployeeLeave(
			EmployeeLeaveTransfer transfer) {
		return employeeLeaveService.save(transfer);
	}

	@GET
	public Page<EmployeeLeaveTransfer> findAllEmployeeLeave(
			@BeanParam SimplePageRequest pageRequest,
			@BeanParam EmployeeLeaveSpecification spec) {
		return employeeLeaveService.findAll(spec, pageRequest);
	}

}
