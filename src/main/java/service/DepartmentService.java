package service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import transfer.DepartmentTransfer;
import entity.Department;

public interface DepartmentService {

	public DepartmentTransfer retrieve(long id);

	public void delete(long id);

	public DepartmentTransfer save(DepartmentTransfer department);

	public Page<DepartmentTransfer> findAll(Specification<Department> spec,
			Pageable pageable);

	public DepartmentTransfer update(long id, DepartmentTransfer department);

}