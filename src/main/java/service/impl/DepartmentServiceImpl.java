package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import service.DepartmentService;
import transfer.DepartmentTransfer;
import dao.DepartmentDao;
import entity.Department;
import exceptions.DepartmentNotFoundException;
import exceptions.RemovingIntegrityViolationException;

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
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new RemovingIntegrityViolationException(Department.class);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			throw new DepartmentNotFoundException(id);
		}
	}

	@Override
	public DepartmentTransfer save(DepartmentTransfer department) {
		department.setId(null);
		Department dep = new Department();
		setUpDepartment(department, dep);
		dep = departmentDao.save(dep);
		return toDepartmentTransfer(dep);
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
	public Page<DepartmentTransfer> findAll(Specification<Department> spec,
			Pageable pageable) {
		List<DepartmentTransfer> transfers = new ArrayList<DepartmentTransfer>();
		Page<Department> departments = departmentDao.findAll(spec, pageable);
		for (Department department : departments) {
			transfers.add(toDepartmentTransfer(department));
		}
		Page<DepartmentTransfer> rets = new PageImpl<DepartmentTransfer>(
				transfers, pageable, departments.getTotalElements());
		return rets;
	}

	private DepartmentTransfer toDepartmentTransfer(Department department) {
		DepartmentTransfer ret = new DepartmentTransfer();
		ret.setId(department.getId());
		ret.setName(department.getName());
		return ret;
	}

}