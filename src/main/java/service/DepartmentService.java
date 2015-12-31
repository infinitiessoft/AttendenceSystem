package service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import entity.Department;
import transfer.DepartmentTransfer;

public interface DepartmentService {

	public DepartmentTransfer retrieve(long id);

	public void delete(long id);

	public DepartmentTransfer save(DepartmentTransfer department);

	public Page<DepartmentTransfer> findAll(Specification<Department> spec,
			Integer page, Integer pageSize, String property, String dir);

	public DepartmentTransfer update(long id, DepartmentTransfer department);

}