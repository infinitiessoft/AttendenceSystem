package resources.version1.admin;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PreAuthorize("hasAuthority('admin')")
public class AdminResource {

	@Autowired
	private EmployeesResource employeesResource;
	@Autowired
	private AttendRecordsResource attendRecordsResource;
	@Autowired
	private AttendRecordTypesResource attendRecordTypesResource;
	@Autowired
	private DepartmentsResource departmentsResource;
	@Autowired
	private EmployeeLeavesResource employeeLeavesResource;
	@Autowired
	private EventsResource eventsResource;
	@Autowired
	private LeavesettingsResource leavesettingsResource;
	@Autowired
	private RolesResource rolesResource;
	@Autowired
	private ReportsResource reportsResource;

	@Path("records")
	public AttendRecordsResource getAttendRecordsResource() {
		return attendRecordsResource;
	}

	@Path("recordtypes")
	public AttendRecordTypesResource getAttendRecordTypesResource() {
		return attendRecordTypesResource;
	}

	@Path("departments")
	public DepartmentsResource getDepartmentsResource() {
		return departmentsResource;
	}

	@Path("employeeleaves")
	public EmployeeLeavesResource getEmployeeLeavesResource() {
		return employeeLeavesResource;
	}

	@Path("employees")
	public EmployeesResource getEmployeesResource() {
		return employeesResource;
	}

	@Path("events")
	public EventsResource getEventsResource() {
		return eventsResource;
	}

	@Path("leavesettings")
	public LeavesettingsResource getLeavesettingsResource() {
		return leavesettingsResource;
	}

	@Path("roles")
	public RolesResource getRolesResource() {
		return rolesResource;
	}

	@Path("reports")
	public ReportsResource getReportsResource() {
		return reportsResource;
	}

}
