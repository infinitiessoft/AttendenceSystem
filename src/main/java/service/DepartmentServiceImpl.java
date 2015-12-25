package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.DepartmentDao;
import dao.DepartmentDao;
import entity.Department;
import entity.Department;
import exceptions.DepartmentNotFoundException;
import transfer.DepartmentTransfer;

public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;

	public DepartmentServiceImpl(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Override
	public DepartmentTransfer retrieve(long id) {
		Department department = departmentDao.find(id);
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
	public DepartmentTransfer save(Department department) {
		department.setId(null);
		return toDepartmentTransfer(departmentDao.save(department));
	}

	@Override
	public DepartmentTransfer update(long id, Department updated) {
		Department department = departmentDao.find(id);
		if (department == null) {
			throw new DepartmentNotFoundException(id);
		}
		updated.setId(department.getId());
		return toDepartmentTransfer(departmentDao.save(updated));
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