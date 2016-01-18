package dao;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import entity.AttendRecord;
import entity.AttendRecordType;
import entity.Department;
import entity.Employee;
import entity.EmployeeLeave;
import entity.EmployeeRole;
import entity.Gender;
import entity.Leavesetting;
import entity.Role;
import transfer.AttendRecordTransfer;

public class DataBaseInitializer {

	private EmployeeDao employeeDao;
	private RoleDao roleDao;
	private DepartmentDao depDao;
	private AttendRecordTypeDao recordTypeDao;
	private AttendRecordDao recordDao;
	private EmployeeRoleDao employeeRoleDao;
	private LeavesettingDao leavesettingDao;
	private EmployeeLeaveDao employeeLeaveDao;
	private PasswordEncoder passwordEncoder;

	public DataBaseInitializer(EmployeeDao employeeDao, RoleDao roleDao,
			DepartmentDao depDao, AttendRecordTypeDao recordTypeDao,
			EmployeeRoleDao employeeRoleDao, LeavesettingDao leavesettingDao,
			AttendRecordDao recordDao, EmployeeLeaveDao employeeLeaveDao,
			PasswordEncoder passwordEncoder) {
		super();
		this.employeeDao = employeeDao;
		this.roleDao = roleDao;
		this.depDao = depDao;
		this.recordTypeDao = recordTypeDao;
		this.employeeRoleDao = employeeRoleDao;
		this.passwordEncoder = passwordEncoder;
		this.leavesettingDao = leavesettingDao;
		this.recordDao = recordDao;
		this.employeeLeaveDao = employeeLeaveDao;
	}

	public void initDataBase() {
		AttendRecordType annual = new AttendRecordType();
		annual.setName("annual");
		recordTypeDao.save(annual);

		AttendRecordType sick = new AttendRecordType();
		sick.setName("sick");
		recordTypeDao.save(sick);

		AttendRecordType personal = new AttendRecordType();
		personal.setName("personal");
		recordTypeDao.save(personal);

		AttendRecordType official = new AttendRecordType();
		official.setName("official");
		recordTypeDao.save(official);

		AttendRecordType other = new AttendRecordType();
		other.setName("other");
		recordTypeDao.save(other);

		Department dep = new Department();
		dep.setManager_id(1l);
		dep.setResponseto(1l);
		dep.setName("sale");
		dep = depDao.save(dep);

		Employee demo2 = new Employee();
		demo2.setDateofjoined(new Date());
		demo2.setEmail("demo2@gmail.com");
		demo2.setName("demo2");
		demo2.setPassword(this.passwordEncoder.encode("demo2"));
		demo2.setUsername("demo2");
		demo2.setGender(Gender.male.name());
		demo2.setDepartment(dep);
		demo2 = this.employeeDao.save(demo2);

		Employee demo = new Employee();
		demo.setDateofjoined(new Date());
		demo.setEmail("demo@gmail.com");
		demo.setName("demo");
		demo.setPassword(this.passwordEncoder.encode("demo"));
		demo.setUsername("demo");
		demo.setEmployee(demo2);
		Role role = new Role();
		role.setName("admin");
		Role user = new Role();
		user.setName("user");
		demo.setGender(Gender.male.name());
		role = roleDao.save(role);
		user = roleDao.save(user);
		demo.setDepartment(dep);
		demo = this.employeeDao.save(demo);

		Employee admin = new Employee();
		admin.setDateofjoined(new Date());
		admin.setEmail("user@gmail.com");
		admin.setName("User");
		admin.setPassword(this.passwordEncoder.encode("user"));
		admin.setUsername("user");
		admin.setGender(Gender.male.name());
		admin.setDepartment(dep);
		admin.setEmployee(demo);
		this.employeeDao.save(admin);

		EmployeeRole adminEmployee = new EmployeeRole();
		adminEmployee.setEmployee(admin);
		adminEmployee.setRole(role);
		employeeRoleDao.save(adminEmployee);

		EmployeeRole demoEmployee = new EmployeeRole();
		demoEmployee.setEmployee(demo);
		demoEmployee.setRole(user);
		employeeRoleDao.save(demoEmployee);

		EmployeeRole demo2Employee = new EmployeeRole();
		demo2Employee.setEmployee(demo2);
		demo2Employee.setRole(user);
		employeeRoleDao.save(demo2Employee);

		for (int i = 0; i < 200; i++) {
			AttendRecord record = new AttendRecord();
			record.setBookDate(new Date());
			record.setDuration(1D);
			record.setEmployee(admin);
			record.setEndDate(new Date());
			record.setReason("fake demo:" + i);
			record.setStartDate(new Date());
			record.setType(annual);
			record.setStatus(AttendRecordTransfer.Status.permit.name());
			recordDao.save(record);
		}

		Leavesetting annual1 = new Leavesetting();
		annual1.setDays(3d);
		annual1.setName("first_year_annual");
		annual1.setYear(1l);
		annual1.setType(annual);
		annual1 = leavesettingDao.save(annual1);

		Leavesetting personal1 = new Leavesetting();
		personal1.setDays(3d);
		personal1.setName("first_year_personal");
		personal1.setYear(1l);
		personal1.setType(personal);
		personal1 = leavesettingDao.save(personal1);

		Leavesetting sick1 = new Leavesetting();
		sick1.setDays(3d);
		sick1.setName("first_year_sick");
		sick1.setYear(1l);
		sick1.setType(sick);
		sick1 = leavesettingDao.save(sick1);

		EmployeeLeave adminAnnual = new EmployeeLeave();
		adminAnnual.setEmployee(admin);
		adminAnnual.setLeavesetting(annual1);
		adminAnnual.setUsedDays(0d);
		adminAnnual = employeeLeaveDao.save(adminAnnual);

		EmployeeLeave adminSick = new EmployeeLeave();
		adminSick.setEmployee(admin);
		adminSick.setLeavesetting(sick1);
		adminSick.setUsedDays(0d);
		adminSick = employeeLeaveDao.save(adminSick);

		EmployeeLeave adminPersonal = new EmployeeLeave();
		adminPersonal.setEmployee(admin);
		adminPersonal.setLeavesetting(personal1);
		adminPersonal.setUsedDays(0d);
		adminPersonal = employeeLeaveDao.save(adminPersonal);
	}
}
