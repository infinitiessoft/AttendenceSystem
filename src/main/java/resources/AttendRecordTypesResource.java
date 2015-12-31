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

import resources.specification.AttendRecordTypeSpecification;
import resources.specification.SimplePageRequest;
import service.AttendRecordTypeService;
import transfer.AttendRecordTypeTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/recordtype")
public class AttendRecordTypesResource {

	@Autowired
	private AttendRecordTypeService attendRecordTypeService;

	@GET
	@Path(value = "{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public AttendRecordTypeTransfer getAttendRecordType(@PathParam("id") long id) {
		return attendRecordTypeService.retrieve(id);
	}

	// ** Method to delete
	@DELETE
	@Path(value = "{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAttendRecordType(@PathParam("id") long id) {
		attendRecordTypeService.delete(id);
		return Response.status(Status.OK).type(MediaType.APPLICATION_JSON)
				.build();
	}

	// **Method to update
	@PUT
	@Path(value = "{id}")
	public AttendRecordTypeTransfer updateAttendRecordType(
			@PathParam("id") long id, AttendRecordTypeTransfer attendRecordType) {
		return attendRecordTypeService.update(id, attendRecordType);
	}

	// **Method to save or create
	@POST
	public AttendRecordTypeTransfer saveAttendRecordType(
			AttendRecordTypeTransfer attendRecordType) {
		return attendRecordTypeService.save(attendRecordType);
	}

	// ** Method to find All the attendRecordTypes in the list

	@GET
	public Page<AttendRecordTypeTransfer> findallAttendRecordType(
			@BeanParam SimplePageRequest pageRequest,
			@BeanParam AttendRecordTypeSpecification spec) {
		return attendRecordTypeService.findAll(spec, pageRequest);
	}
}
