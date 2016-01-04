package dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import entity.EmployeeRole;

public interface EmployeeRoleDao extends
		PagingAndSortingRepository<EmployeeRole, Long>,
		JpaSpecificationExecutor<EmployeeRole> {
	EmployeeRole findByEmployeeIdAndRoleId(Long employeeId, Long roleId);

}
