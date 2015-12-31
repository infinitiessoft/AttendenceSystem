package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import resources.Config;
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
	public Page<DepartmentTransfer> findAll(Specification<Department> spec,
			Integer page, Integer pageSize, String property, String dir) {
		if (page == null) {
			page = 0;
		}
		if (pageSize == null) {
			pageSize = Integer.parseInt(Config.getProperty(Config.PAGE_SIZE));
		}
		if (property == null) {
			property = "id";
		}
		if (dir == null) {
			dir = Direction.ASC.name();
		}
		Sort sort = new Sort(Direction.valueOf(dir), property);
		Pageable pageable = new PageRequest(page, pageSize, sort);
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