package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import resources.specification.EmployeeLeaveSpecification;
import transfer.EmployeeLeaveTransfer;

public interface EmployeeLeaveService {

	public EmployeeLeaveTransfer retrieve(long id);

	public void delete(long id);

	public EmployeeLeaveTransfer save(EmployeeLeaveTransfer employeeLeave);

	public Page<EmployeeLeaveTransfer> findAll(EmployeeLeaveSpecification spec, Pageable pageable);

	public EmployeeLeaveTransfer update(long id, EmployeeLeaveTransfer employeeLeave);
}
