package service;

import java.util.Collection;

import transfer.DepartmentTransfer;

public interface DepartmentService {

	public DepartmentTransfer retrieve(long id);

	public void delete(long id);

	public DepartmentTransfer save(DepartmentTransfer department);

	public Collection<DepartmentTransfer> findAll();

	public DepartmentTransfer update(long id, DepartmentTransfer department);

}