package resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import resources.version1.admin.AdminResource;
import resources.version1.general.GeneralResource;
import resources.version1.member.AuthResource;
import resources.version1.member.MemberAttendRecordTypesResource;
import resources.version1.member.MemberDepartmentsResource;
import resources.version1.member.MembersResource;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("v1.0")
public class Version1_1Resource {

	@Autowired
	private AdminResource adminResource;
	@Autowired
	private AuthResource authResource;
	@Autowired
	private MembersResource membersResource;
	@Autowired
	private MemberDepartmentsResource memberDepartmentsResource;
	@Autowired
	private MemberAttendRecordTypesResource memberAttendRecordTypesResource;
	@Autowired
	private GeneralResource generalResource;

	@Path("admin")
	public AdminResource getAdminResource() {
		return adminResource;
	}

	@Path("auth")
	public AuthResource getAuthResource() {
		return authResource;
	}

	@Path("employees")
	public MembersResource getEmployeesResource() {
		return membersResource;
	}

	@Path("departments")
	public MemberDepartmentsResource getDepartmentsResource() {
		return memberDepartmentsResource;
	}

	@Path("recordtypes")
	public MemberAttendRecordTypesResource getAttendRecordTypesResource() {
		return memberAttendRecordTypesResource;
	}

	@Path("general")
	public GeneralResource getGeneralResource() {
		return generalResource;
	}
}
