package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;

import resources.Config;
import transfer.EmployeeTransfer;
import transfer.EmployeeTransfer.Department;
import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Employee;
import entity.Gender;
import exceptions.DepartmentNotFoundException;
import exceptions.EmployeeNotFoundException;

/**
 * One implementation of the interface for Employee Service
 * 
 */
public class EmployeeServiceImpl implements EmployeeService {

	private EmployeeDao employeeDao;
	private DepartmentDao departmentDao;
	private PasswordEncoder passwordEncoder;

	public EmployeeServiceImpl(EmployeeDao employeeDao,
			DepartmentDao departmentDao, PasswordEncoder passwordEncoder) {
		this.employeeDao = employeeDao;
		this.departmentDao = departmentDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public EmployeeTransfer retrieve(long id) {
		Employee employee = employeeDao.findOne(id);
		if (employee == null) {
			throw new EmployeeNotFoundException(id);
		}
		return toEmployeeTransfer(employee);
	}

	@Override
	public void delete(long id) {
		try {
			employeeDao.delete(id);
		} catch (NullPointerException e) {
			throw new EmployeeNotFoundException(id);
		}
	}

	@Override
	public EmployeeTransfer save(EmployeeTransfer employee) {
		employee.setId(null);
		Employee newEntry = new Employee();
		setUpEmployee(employee, newEntry);
		return toEmployeeTransfer(employeeDao.save(newEntry));
	}

	private void setUpEmployee(EmployeeTransfer transfer, Employee newEntry) {
		if (transfer.isDateOfJoinedSet()) {
			newEntry.setDateofjoined(transfer.getDateOfJoined());
		}
		if (transfer.isEmailSet()) {
			newEntry.setEmail(transfer.getEmail());
		}
		if (transfer.isNameSet()) {
			newEntry.setName(transfer.getName());
		}
		if (transfer.isUsernameSet()) {
			newEntry.setUsername(transfer.getUsername());
		}
		if (transfer.isGenderSet()) {
			newEntry.setGender(Gender.valueOf(transfer.getGender()));
		}
		if (transfer.isPasswordSet()) {
			newEntry.setPassword(passwordEncoder.encode(transfer.getPassword()));
		}
		if (transfer.isDepartmentSet()) {
			if (transfer.getDepartment().isIdSet()) {
				entity.Department department = departmentDao.findOne(transfer
						.getDepartment().getId());
				if (department == null) {
					throw new DepartmentNotFoundException(transfer
							.getDepartment().getId());
				}
				newEntry.setDepartment(department);
			}
		}

	}

	@Override
	public EmployeeTransfer update(long id, EmployeeTransfer updated) {
		Employee employee = employeeDao.findOne(id);
		if (employee == null) {
			throw new EmployeeNotFoundException(id);
		}
		setUpEmployee(updated, employee);
		return toEmployeeTransfer(employeeDao.save(employee));
	}

	@Override
	public Page<EmployeeTransfer> findAll(Integer page, Integer pageSize,
			String property, String dir) {
		if (page == null) {
			page = 0;
		}
		if (pageSize == null) {
			pageSize = Integer.parseInt(Config.getProperty(Config.PAGE_SIZE));
		}
		if (property == null) {
			property = "id";
		}
		if (dir == null) {
			dir = Direction.ASC.name();
		}
		Sort sort = new Sort(Direction.valueOf(dir), property);
		Pageable pageable = new PageRequest(page, pageSize, sort);
		List<EmployeeTransfer> transfers = new ArrayList<EmployeeTransfer>();
		Page<Employee> employees = employeeDao.findAll(pageable);
		for (Employee employee : employees) {
			transfers.add(toEmployeeTransfer(employee));
		}
		Page<EmployeeTransfer> rets = new PageImpl<EmployeeTransfer>(transfers,
				pageable, employees.getTotalElements());
		return rets;
	}

	private EmployeeTransfer toEmployeeTransfer(Employee employee) {
		EmployeeTransfer ret = new EmployeeTransfer();
		ret.setId(employee.getId());
		ret.setUsername(employee.getUsername());
		ret.setName(employee.getName());
		ret.setDateOfJoined(employee.getDateofjoined());
		ret.setEmail(employee.getEmail());
		ret.setGender(employee.getGender().name());
		// ret.setPassword(employee.getPassword());
		ret.setDepartment(new Department(employee.getDepartment().getId(),
				employee.getDepartment().getName()));
		return ret;
	}

	@Override
	public EmployeeTransfer findByUsername(String username) {
		return toEmployeeTransfer(employeeDao.findByUsername(username));
	}
}