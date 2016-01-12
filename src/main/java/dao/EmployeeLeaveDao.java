package dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import entity.EmployeeLeave;

public interface EmployeeLeaveDao extends PagingAndSortingRepository<EmployeeLeave, Long>, JpaSpecificationExecutor<EmployeeLeave> {

	EmployeeLeave findByEmployeeIdAndLeavesettingId(Long employeeId, Long leavesettingId);
}
