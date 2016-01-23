package resources.version1.admin;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.security.access.prepost.PreAuthorize;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PreAuthorize("hasAuthority('admin')")
public class AdminResource {

	@Path("records")
	public Class<AttendRecordsResource> getAttendRecordsResource() {
		return AttendRecordsResource.class;
	}

	@Path("recordtypes")
	public Class<AttendRecordTypesResource> getAttendRecordTypesResource() {
		return AttendRecordTypesResource.class;
	}

	@Path("departments")
	public Class<DepartmentsResource> getDepartmentsResource() {
		return DepartmentsResource.class;
	}

	@Path("employeeleaves")
	public Class<EmployeeLeavesResource> getEmployeeLeavesResource() {
		return EmployeeLeavesResource.class;
	}

	@Path("employees")
	public Class<EmployeesResource> getEmployeesResource() {
		return EmployeesResource.class;
	}

	@Path("events")
	public Class<EventsResource> getEventsResource() {
		return EventsResource.class;
	}

	@Path("leavesettings")
	public Class<LeavesettingsResource> getLeavesettingsResource() {
		return LeavesettingsResource.class;
	}

	@Path("roles")
	public Class<RolesResource> getRolesResource() {
		return RolesResource.class;
	}

	@Path("reports")
	public Class<ReportsResource> getReportsResource() {
		return ReportsResource.class;
	}

}
