package dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import entity.Department;

public interface DepartmentDao extends
		PagingAndSortingRepository<Department, Long>,
		JpaSpecificationExecutor<Department> {

}
