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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import resources.specification.AttendRecordSpecification;
import resources.specification.SimplePageRequest;
import service.AttendRecordService;
import transfer.AttendRecordTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeRecordsResource {

	@Autowired
	private AttendRecordService attendRecordService;

	@GET
	@Path(value = "{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AttendRecordTransfer getAttendRecord(@PathParam("id") long id) {
		return attendRecordService.retrieve(id);
	}

	// ** Method to delete
	@DELETE
	@Path(value = "{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAttendRecord(@PathParam("id") long id) {
		attendRecordService.delete(id);
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON)
				.build();
	}

	// **Method to update
	@PUT
	@Path(value = "{id}")
	public AttendRecordTransfer updateAttendRecord(@PathParam("id") long id,
			AttendRecordTransfer attendRecord) {
		return attendRecordService.update(id, attendRecord);
	}

	// **Method to save or create
	@POST
	public AttendRecordTransfer saveAttendRecord(
			AttendRecordTransfer attendRecord) {
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
