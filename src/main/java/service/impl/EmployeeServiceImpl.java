package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import resources.specification.EmployeeSpecification;
import service.EmployeeService;
import transfer.EmployeeTransfer;
import dao.DepartmentDao;
import dao.EmployeeDao;
import entity.Employee;
import entity.Gender;
import exceptions.DepartmentNotFoundException;
import exceptions.EmployeeDeletionNotAllowException;
import exceptions.EmployeeNotFoundException;
import exceptions.InvalidGenderException;
import exceptions.RemovingIntegrityViolationException;

/**
 * One implementation of the interface for Employee Service
 * 
 */
public class EmployeeServiceImpl implements EmployeeService {

	// private static final Logger logger =
	// LoggerFactory.getLogger(EmployeeServiceImpl.class);
	private EmployeeDao employeeDao;
	private DepartmentDao departmentDao;
	private PasswordEncoder passwordEncoder;

	public EmployeeServiceImpl(EmployeeDao employeeDao,
			DepartmentDao departmentDao, PasswordEncoder passwordEncoder) {
		this.employeeDao = employeeDao;
		this.departmentDao = departmentDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	@Override
	public EmployeeTransfer retrieve(long id) {
		Employee employee = employeeDao.findOne(id);
		if (employee == null) {
			throw new EmployeeNotFoundException(id);
		}
		return toEmployeeTransfer(employee);
	}

	@Transactional
	@Override
	public void delete(long id) {
		try {
			Employee employee = employeeDao.findOne(id);
			if (employee == null) {
				throw new EmployeeNotFoundException(id);
			}
			if (employee.getEmployees().size() > 0) {
				throw new EmployeeDeletionNotAllowException(id);
			}
			employeeDao.delete(employee);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new RemovingIntegrityViolationException(Employee.class);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			throw new EmployeeNotFoundException(id);
		}
	}

	@Transactional
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
			try {
				Gender.valueOf(transfer.getGender());
			} catch (Exception e) {
				throw new InvalidGenderException(transfer.getGender());
			}
			newEntry.setGender(transfer.getGender());
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
		if (transfer.isEmployeeSet()) {
			if (transfer.getEmployee().isIdSet()) {
				entity.Employee emp = employeeDao.findOne(transfer
						.getEmployee().getId());
				if (emp == null) {
					throw new EmployeeNotFoundException(transfer.getEmployee()
							.getId());
				}
				newEntry.setEmployee(emp);
			}
		}
	}

	@Transactional
	@Override
	public EmployeeTransfer update(long id, EmployeeTransfer updated) {
		Employee employee = employeeDao.findOne(id);
		if (employee == null) {
			throw new EmployeeNotFoundException(id);
		}
		setUpEmployee(updated, employee);
		return toEmployeeTransfer(employeeDao.save(employee));
	}

	@Transactional
	@Override
	public Page<EmployeeTransfer> findAll(EmployeeSpecification spec,
			Pageable pageable) {
		List<EmployeeTransfer> transfers = new ArrayList<EmployeeTransfer>();
		Page<Employee> employees = employeeDao.findAll(spec, pageable);
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
		ret.setGender(employee.getGender());
		// ret.setPassword(employee.getPassword());
		EmployeeTransfer.Department dep = new EmployeeTransfer.Department();
		dep.setId(employee.getDepartment().getId());
		dep.setName(employee.getDepartment().getName());
		ret.setDepartment(dep);
		if (employee.getEmployee() != null) {
			EmployeeTransfer.Employee emp = new EmployeeTransfer.Employee();
			emp.setId(employee.getEmployee().getId());
			emp.setName(employee.getEmployee().getName());
			ret.setEmployee(emp);
		}

		return ret;
	}

	@Transactional
	@Override
	public EmployeeTransfer findByUsername(String username) {
		return toEmployeeTransfer(employeeDao.findByUsername(username));
	}
}