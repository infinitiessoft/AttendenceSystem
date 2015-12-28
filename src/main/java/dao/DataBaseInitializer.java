package dao;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import entity.Department;
import entity.Employee;
import entity.Gender;
import entity.Role;

public class DataBaseInitializer {

	private EmployeeDao employeeDao;
	private RoleDao roleDao;
	private DepartmentDao depDao;
	private PasswordEncoder passwordEncoder;

	public DataBaseInitializer(EmployeeDao employeeDao, RoleDao roleDao,
			DepartmentDao depDao, PasswordEncoder passwordEncoder) {
		super();
		this.employeeDao = employeeDao;
		this.roleDao = roleDao;
		this.depDao = depDao;
		this.passwordEncoder = passwordEncoder;
	}

	public void initDataBase() {
		Department dep = new Department();
		dep.setManager_id(1l);
		dep.setResponseto(1l);
		dep.setName("sale");
		dep = depDao.save(dep);
		
		Employee admin = new Employee();
		admin.setDateofjoined(new Date());
		admin.setEmail("pohsun@gmail.com");
		admin.setName("pohsun, Huang");
		admin.setPassword(this.passwordEncoder.encode("2ggudoou"));
		admin.setUsername("pohsun");
		Role role = new Role();
		role.setName("admin");
		Role user = new Role();
		user.setName("user");
		admin.setGender(Gender.male);
		role = roleDao.save(role);
		user = roleDao.save(user);
		admin.getRoles().add(role);
		admin.getRoles().add(user);
//		dep = depDao.find(dep.getId());
		admin.setDepartment(dep);
		this.employeeDao.save(admin);
		dep.getEmployees().add(admin);
	}

}
