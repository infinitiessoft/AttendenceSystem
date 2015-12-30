package dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import entity.Employee;

public interface EmployeeDao extends PagingAndSortingRepository<Employee, Long> {
	Employee findByUsername(String username);
}
