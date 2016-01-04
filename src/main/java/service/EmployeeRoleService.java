package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import resources.specification.EmployeeRoleSpecification;
import transfer.RoleTransfer;

public interface EmployeeRoleService {

	public void delete(long employeeId, long roleId);

	public Page<RoleTransfer> findAll(EmployeeRoleSpecification spec,
			Pageable pageable);

	public RoleTransfer findByEmployeeIdAndRoleId(long employeeId, long roleId);

	public void assignRoleToEmployee(long employeeId, long roleId);

}