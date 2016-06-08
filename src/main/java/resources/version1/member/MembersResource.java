package resources.version1.member;

import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import resources.specification.EmployeeLeaveSpecification;
import resources.specification.EmployeeSpecification;
import resources.specification.SimplePageRequest;
import service.EmployeeLeaveService;
import service.EmployeeService;
import transfer.EmployeeLeaveTransfer;
import transfer.EmployeeTransfer;
import util.CalendarUtils;
import exceptions.EmployeeLeaveNotFoundException;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/employees")
public class MembersResource {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeLeaveService employeeLeaveService;

	@Autowired
	private MemberRolesResource memberRolesResource;

	@Autowired
	private MemberAttendRecordsResource memberAttendRecordsResource;

	@Autowired
	private MemberEventsResource memberEventsResource;

	@GET
	@Path(value = "{id}")
	@PreAuthorize("isAuthenticated() and #id == principal.id")
	public EmployeeTransfer getEmployee(@PathParam("id") long id) {
		return employeeService.retrieve(id);
	}

	// **Method to update
	@PUT
	@Path(value = "{id}")
	@PreAuthorize("isAuthenticated() and #id == principal.id")
	public EmployeeTransfer updateEmployee(@PathParam("id") long id,
			EmployeeTransfer employee) {
		if (employee != null) {
			employee.setDepartment(null);
			employee.setDepartmentSet(false);
			employee.setEmployee(null);
			employee.setEmployeeSet(false);
			employee.setRoles(new ArrayList<EmployeeTransfer.Role>(0));
		}
		return employeeService.update(id, employee);
	}

	// ** Method to find All the employees in the list
	@GET
	@PreAuthorize("isAuthenticated()")
	public Page<EmployeeTransfer> findAllEmployee(
			@BeanParam SimplePageRequest pageRequest,
			@BeanParam EmployeeSpecification spec) {
		return employeeService.findAll(spec, pageRequest);
	}

	@Path("{id}/roles")
	public MemberRolesResource getEmployeeRolesResource() {
		return memberRolesResource;
	}

	@Path("{id}/records")
	public MemberAttendRecordsResource getEmployeeAttendRecordsResource() {
		return memberAttendRecordsResource;
	}

	@Path("{id}/events")
	public MemberEventsResource getEmployeeEventsResource() {
		return memberEventsResource;
	}

	@GET
	@Path("{id}/employeeLeaves")
	@PreAuthorize("isAuthenticated() and #id == principal.id")
	public EmployeeLeaveTransfer findEmployeeLeave(@PathParam("id") long id,
			@BeanParam EmployeeLeaveSpecification spec) {
		EmployeeTransfer employee = employeeService.retrieve(id);
		long year = CalendarUtils.getYearOfJoined(employee.getDateOfJoined(),
				new Date());
		if (spec == null) {
			spec = new EmployeeLeaveSpecification();
		}
		spec.setEmployeeId(id);
		spec.setYear(year);

		EmployeeLeaveTransfer employeeLeave;
		try {
			employeeLeave = employeeLeaveService.retrieve(spec);
		} catch (EmployeeLeaveNotFoundException e) {
			employeeLeave = new EmployeeLeaveTransfer();
			employeeLeave.setUsedDays(0d);
			EmployeeLeaveTransfer.Leavesetting setting = new EmployeeLeaveTransfer.Leavesetting();
			setting.setDays(0d);
			employeeLeave.setLeavesetting(setting);
		}
		return employeeLeave;
	}

}
