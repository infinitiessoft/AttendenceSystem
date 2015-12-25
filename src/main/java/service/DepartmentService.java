package service;

import java.util.Collection;

import entity.Department;
import transfer.DepartmentTransfer;

public interface DepartmentService {

	public DepartmentTransfer retrieve(long id);

	public void delete(long id);

	public DepartmentTransfer save(Department department);

	public Collection<DepartmentTransfer> findAll();

	public DepartmentTransfer update(long id, Department department);

}