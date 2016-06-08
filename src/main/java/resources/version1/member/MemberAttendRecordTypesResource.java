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

import resources.specification.AttendRecordTypeSpecification;
import resources.specification.SimplePageRequest;
import service.AttendRecordTypeService;
import transfer.AttendRecordTypeTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PreAuthorize("isAuthenticated()")
@Path("/recordtypes")
public class MemberAttendRecordTypesResource {

	@Autowired
	private AttendRecordTypeService attendRecordTypeService;

	@GET
	@Path(value = "{id}")
	public AttendRecordTypeTransfer getAttendRecordType(@PathParam("id") long id) {
		return attendRecordTypeService.retrieve(id);
	}

	@GET
	public Page<AttendRecordTypeTransfer> findallAttendRecordType(
			@BeanParam SimplePageRequest pageRequest,
			@BeanParam AttendRecordTypeSpecification spec) {
		return attendRecordTypeService.findAll(spec, pageRequest);
	}
}
