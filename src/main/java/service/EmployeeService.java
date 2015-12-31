package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import resources.specification.EmployeeSpecification;
import transfer.EmployeeTransfer;

/**
 * An interface that defines what a Employee Services looks like in general
 *
 */
public interface EmployeeService {

	public EmployeeTransfer retrieve(long id);

	public EmployeeTransfer findByUsername(String username);

	public void delete(long id);

	public EmployeeTransfer save(EmployeeTransfer employee);

	public Page<EmployeeTransfer> findAll(EmployeeSpecification spec,
			Pageable pageable);

	public EmployeeTransfer update(long id, EmployeeTransfer employee);

}