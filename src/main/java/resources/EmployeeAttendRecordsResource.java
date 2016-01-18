package resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import resources.specification.AttendRecordSpecification;
import resources.specification.SimplePageRequest;
import service.AttendRecordService;
import transfer.AttendRecordTransfer;
import transfer.AttendRecordTransfer.Employee;
import exceptions.AttendRecordNotFoundException;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeAttendRecordsResource {

	@Autowired
	private AttendRecordService attendRecordService;

	// @Autowired
	// private EmployeeService employeeService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@PreAuthorize("isAuthenticated() and #id == principal.id or hasAuthority('admin')")
	public Page<AttendRecordTransfer> findAll(@PathParam("id") long id,
			@BeanParam AttendRecordSpecification spec,
			@BeanParam SimplePageRequest pageRequest) {
		spec.setApplicantId(id);
		return attendRecordService.findAll(spec, pageRequest);
	}

	@GET
	@Path(value = "{recordid}")
	@Produces(MediaType.APPLICATION_JSON)
	@PreAuthorize("isAuthenticated() and #id == principal.id or hasAuthority('admin')")
	public AttendRecordTransfer getAttendRecord(@PathParam("id") long id,
			@PathParam("recordid") long recordId) {
		AttendRecordSpecification spec = new AttendRecordSpecification();
		spec.setApplicantId(id);
		spec.setId(recordId);
		Page<AttendRecordTransfer> lists = attendRecordService.findAll(spec,
				null);
		AttendRecordTransfer ret = lists.getContent().size() > 0 ? lists
				.getContent().get(0) : null;
		if (ret == null) {
			throw new AttendRecordNotFoundException(recordId);
		}
		return ret;
	}

	// **Method to save or create
	@POST
	@PreAuthorize("isAuthenticated() and #id == principal.id or hasAuthority('admin')")
	public AttendRecordTransfer saveAttendRecord(@PathParam("id") long id,
			@Context SecurityContext sc, AttendRecordTransfer attendRecord) {
		// EmployeeTransfer employee = employeeService.findByUsername(sc
		// .getUserPrincipal().getName());
		Employee e = new Employee();
		e.setId(id);
		attendRecord.setApplicant(e);
		return attendRecordService.save(attendRecord);
	}
}
