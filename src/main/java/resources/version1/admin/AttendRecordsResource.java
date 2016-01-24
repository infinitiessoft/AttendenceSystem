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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import resources.specification.AttendRecordSpecification;
import resources.specification.SimplePageRequest;
import service.AttendRecordService;
import service.EmployeeService;
import transfer.AttendRecordTransfer;
import transfer.AttendRecordTransfer.Employee;
import transfer.EmployeeTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/records")
public class AttendRecordsResource {

	@Autowired
	private AttendRecordService attendRecordService;

	@Autowired
	private EmployeeService employeeService;

	@GET
	@Path(value = "{id}")
	public AttendRecordTransfer getAttendRecord(@PathParam("id") long id) {
		return attendRecordService.retrieve(id);
	}

	// ** Method to delete
	@DELETE
	@Path(value = "{id}")
	public Response deleteAttendRecord(@PathParam("id") long id) {
		attendRecordService.delete(id, true);
		return Response.status(Status.NO_CONTENT).type(MediaType.APPLICATION_JSON)
				.build();
	}

	// **Method to update
	@PUT
	@Path(value = "{id}")
	public AttendRecordTransfer updateAttendRecord(@PathParam("id") long id,
			AttendRecordTransfer attendRecord) {
		return attendRecordService.update(id, attendRecord, true);
	}

	// **Method to save or create
	@POST
	public AttendRecordTransfer saveAttendRecord(@Context SecurityContext sc,
			AttendRecordTransfer attendRecord) {
		EmployeeTransfer employee = employeeService.findByUsername(sc
				.getUserPrincipal().getName());
		Employee e = new Employee();
		e.setId(employee.getId());
		attendRecord.setApplicant(e);
		return attendRecordService.save(attendRecord);
	}

	// ** Method to find All the attendRecords in the list

	@GET
	public Page<AttendRecordTransfer> findallAttendRecord(
			@BeanParam SimplePageRequest pageRequest,
			@BeanParam AttendRecordSpecification spec) {
		return attendRecordService.findAll(spec, pageRequest);
	}

}
