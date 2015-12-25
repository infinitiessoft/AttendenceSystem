package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.DepartmentDao;
import transfer.DepartmentTransfer;

public class DepartmentServiceImpl implements DepartmentService {

	private DepartmentDao departmentDao;

	public DepartmentServiceImpl(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Override
	public DepartmentTransfer retrieve(long id) {
		return toDepartmentTransfer(departmentDao.find(id));
	}

	@Override
	public void delete(long id) {
		departmentDao.delete(id);
	}

	@Override
	public DepartmentTransfer save(Department department) {
		return toDepartmentTransfer(departmentDao.save(department));
	}

	@Override
	public DepartmentTransfer update(long id, Department department) {
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
		ret.setName(department.getname());
		// ret.setRoles(this.createRoleMap(department));
		return ret;
	}

	@Override
	public DepartmentTransfer findByname(String name) {
		return toDepartmentTransfer(departmentDao.findByname(name));
	}
}