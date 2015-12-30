package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import transfer.DepartmentTransfer;
import dao.DepartmentDao;
import entity.Department;
import exceptions.DepartmentNotFoundException;

public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;

	public DepartmentServiceImpl(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Override
	public DepartmentTransfer retrieve(long id) {
		Department department = departmentDao.findOne(id);
		if (department == null) {
			throw new DepartmentNotFoundException(id);
		}
		return toDepartmentTransfer(department);
	}

	@Override
	public void delete(long id) {
		try {
			departmentDao.delete(id);
		} catch (NullPointerException e) {
			throw new DepartmentNotFoundException(id);
		}
	}

	@Override
	public DepartmentTransfer save(DepartmentTransfer department) {
		department.setId(null);
		Department dep = new Department();
		setUpDepartment(department, dep);
		return toDepartmentTransfer(departmentDao.save(dep));
	}

	private void setUpDepartment(DepartmentTransfer transfer, Department dep) {
		if (transfer.isNameSet()) {
			dep.setName(transfer.getName());
		}
	}

	@Override
	public DepartmentTransfer update(long id, DepartmentTransfer updated) {
		Department department = departmentDao.findOne(id);
		if (department == null) {
			throw new DepartmentNotFoundException(id);
		}
		setUpDepartment(updated, department);
		return toDepartmentTransfer(departmentDao.save(department));
	}

	@Override
	public Collection<DepartmentTransfer> findAll() {
		List<DepartmentTransfer> rets = new ArrayList<DepartmentTransfer>();

		for (Department department : departmentDao.findAll()) {
			rets.add(toDepartmentTransfer(department));
		}
		return rets;
	}

	private DepartmentTransfer toDepartmentTransfer(Department department) {
		DepartmentTransfer ret = new DepartmentTransfer();
		ret.setId(department.getId());
		ret.setName(department.getName());
		return ret;
	}

}