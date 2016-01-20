package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import resources.specification.EmployeeRoleSpecification;
import service.EmployeeRoleService;
import transfer.RoleTransfer;
import dao.EmployeeDao;
import dao.EmployeeRoleDao;
import dao.RoleDao;
import entity.Employee;
import entity.EmployeeRole;
import entity.Role;
import exceptions.EmployeeNotFoundException;
import exceptions.RoleAssignmentNotFoundException;
import exceptions.RoleNotFoundException;

public class EmployeeRoleServiceImpl implements EmployeeRoleService {

	private EmployeeDao employeeDao;
	private RoleDao roleDao;
	private EmployeeRoleDao employeeRoleDao;

	public EmployeeRoleServiceImpl(EmployeeDao employeeDao, RoleDao roleDao,
			EmployeeRoleDao employeeRoleDao) {
		this.employeeDao = employeeDao;
		this.roleDao = roleDao;
		this.employeeRoleDao = employeeRoleDao;
	}

	@Override
	public Page<RoleTransfer> findAll(EmployeeRoleSpecification spec,
			Pageable pageable) {
		List<RoleTransfer> transfers = new ArrayList<RoleTransfer>();
		Page<EmployeeRole> employees = employeeRoleDao.findAll(spec, pageable);
		for (EmployeeRole employee : employees) {
			Role role = employee.getRole();
			transfers.add(RoleServiceImpl.toRoleTransfer(role));
		}
		Page<RoleTransfer> rets = new PageImpl<RoleTransfer>(transfers,
				pageable, employees.getTotalElements());
		return rets;
	}

	@Override
	public RoleTransfer findByEmployeeIdAndRoleId(long employeeId, long roleId) {
		EmployeeRole employeeRole = employeeRoleDao.findByEmployeeIdAndRoleId(
				employeeId, roleId);
		if (employeeRole == null) {
			throw new RoleAssignmentNotFoundException(employeeId, roleId);
		}
		return RoleServiceImpl.toRoleTransfer(employeeRole.getRole());
	}

	@Override
	public void revokeRoleFromEmployee(long employeeId, long roleId) {
		EmployeeRole employeeRole = employeeRoleDao.findByEmployeeIdAndRoleId(
				employeeId, roleId);
		if (employeeRole == null) {
			throw new RoleAssignmentNotFoundException(employeeId, roleId);
		}
		employeeRoleDao.delete(employeeRole);
	}

	@Override
	public void grantRoleToEmployee(long employeeId, long roleId) {
		Employee employee = employeeDao.findOne(employeeId);
		if (employee == null) {
			throw new EmployeeNotFoundException(employeeId);
		}
		Role role = roleDao.findOne(roleId);
		if (role == null) {
			throw new RoleNotFoundException(roleId);
		}
		EmployeeRole employeeRole = new EmployeeRole();
		employeeRole.setEmployee(employee);
		employeeRole.setRole(role);
		employeeRoleDao.save(employeeRole);
	}

}