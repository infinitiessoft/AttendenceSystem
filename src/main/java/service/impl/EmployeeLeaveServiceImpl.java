package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import resources.specification.EmployeeLeaveSpecification;
import service.EmployeeLeaveService;
import transfer.EmployeeLeaveTransfer;
import transfer.EmployeeLeaveTransfer.Employee;
import transfer.EmployeeLeaveTransfer.Leavesetting;
import dao.EmployeeDao;
import dao.EmployeeLeaveDao;
import dao.LeavesettingDao;
import entity.EmployeeLeave;
import exceptions.EmployeeLeaveNotFoundException;
import exceptions.EmployeeNotFoundException;
import exceptions.LeavesettingNotFoundException;
import exceptions.RemovingIntegrityViolationException;

public class EmployeeLeaveServiceImpl implements EmployeeLeaveService {

	private EmployeeLeaveDao employeeLeaveDao;
	private EmployeeDao employeeDao;
	private LeavesettingDao leavesettingDao;

	public EmployeeLeaveServiceImpl(EmployeeLeaveDao employeeLeaveDao,
			EmployeeDao employeeDao, LeavesettingDao leavesettingDao) {
		this.employeeLeaveDao = employeeLeaveDao;
		this.employeeDao = employeeDao;
		this.leavesettingDao = leavesettingDao;
	}

	@Override
	public EmployeeLeaveTransfer retrieve(long id) {
		EmployeeLeave employeeLeave = employeeLeaveDao.findOne(id);
		if (employeeLeave == null) {
			throw new EmployeeLeaveNotFoundException(id);
		}
		return toEmployeeLeaveTransfer(employeeLeave);
	}

	@Override
	public void delete(long id) {
		try {
			employeeLeaveDao.delete(id);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new RemovingIntegrityViolationException(EmployeeLeave.class);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			throw new EmployeeLeaveNotFoundException(id);
		}
	}

	@Override
	public EmployeeLeaveTransfer save(EmployeeLeaveTransfer employeeLeave) {
		employeeLeave.setId(null);
		EmployeeLeave newEntry = new EmployeeLeave();
		if (employeeLeave.getUsedDays() == null) {
			employeeLeave.setUsedDays(0d);
		}
		setUpEmployeeLeave(employeeLeave, newEntry);
		return toEmployeeLeaveTransfer(employeeLeaveDao.save(newEntry));
	}

	@Override
	public Page<EmployeeLeaveTransfer> findAll(EmployeeLeaveSpecification spec,
			Pageable pageable) {
		List<EmployeeLeaveTransfer> transfers = new ArrayList<EmployeeLeaveTransfer>();
		Page<EmployeeLeave> employeeLeaves = employeeLeaveDao.findAll(spec,
				pageable);
		for (EmployeeLeave employeeLeave : employeeLeaves) {
			transfers.add(toEmployeeLeaveTransfer(employeeLeave));
		}
		Page<EmployeeLeaveTransfer> rets = new PageImpl<EmployeeLeaveTransfer>(
				transfers, pageable, employeeLeaves.getTotalElements());
		return rets;
	}

	@Override
	public EmployeeLeaveTransfer findByEmployeeIdAndLeavesettingId(
			long employeeId, long leavesettingId) {
		EmployeeLeave employeeLeave = employeeLeaveDao
				.findByEmployeeIdAndLeavesettingId(employeeId, leavesettingId);
		if (employeeLeave == null) {
			throw new EmployeeLeaveNotFoundException(employeeId, leavesettingId);
		}
		return toEmployeeLeaveTransfer(employeeLeave);
	}

	@Override
	public EmployeeLeaveTransfer update(long id, EmployeeLeaveTransfer updated) {
		EmployeeLeave employeeLeave = employeeLeaveDao.findOne(id);
		if (employeeLeave == null) {
			throw new EmployeeLeaveNotFoundException(id);
		}
		if (updated.isUsedDaysSet()) {
			employeeLeave.setUsedDays(updated.getUsedDays());
		}
		return toEmployeeLeaveTransfer(employeeLeaveDao.save(employeeLeave));
	}

	private void setUpEmployeeLeave(EmployeeLeaveTransfer transfer,
			EmployeeLeave newEntry) {
		if (transfer.isEmployeeSet()) {
			if (transfer.getEmployee().isIdSet()) {
				entity.Employee employee = employeeDao.findOne(transfer
						.getEmployee().getId());
				if (employee == null) {
					throw new EmployeeNotFoundException(transfer.getEmployee()
							.getId());
				}
				newEntry.setEmployee(employee);
			}
		}
		if (transfer.isLeavesettingSet()) {
			if (transfer.getLeavesetting().isIdSet()) {
				entity.Leavesetting leavesetting = leavesettingDao
						.findOne(transfer.getLeavesetting().getId());
				if (leavesetting == null) {
					throw new LeavesettingNotFoundException(transfer
							.getLeavesetting().getId());
				}
				newEntry.setLeavesetting(leavesetting);
			}
		}
		if (transfer.isUsedDaysSet()) {
			newEntry.setUsedDays(transfer.getUsedDays());
		}
	}

	private EmployeeLeaveTransfer toEmployeeLeaveTransfer(
			EmployeeLeave employeeLeave) {
		EmployeeLeaveTransfer ret = new EmployeeLeaveTransfer();
		ret.setId(employeeLeave.getId());
		ret.setUsedDays(employeeLeave.getUsedDays());

		entity.Employee entity = employeeLeave.getEmployee();
		Employee employee = new EmployeeLeaveTransfer.Employee();
		employee.setId(entity.getId());
		employee.setName(entity.getName());
		// employee.setDateofjoined(entity.getDateofjoined());
		ret.setEmployee(employee);

		entity.Leavesetting leave = employeeLeave.getLeavesetting();
		Leavesetting leavesetting = new EmployeeLeaveTransfer.Leavesetting();
		leavesetting.setId(leave.getId());
		leavesetting.setName(leave.getName());
		leavesetting.setDays(leave.getDays());
		leavesetting.setYear(leave.getYear());
		EmployeeLeaveTransfer.Leavesetting.Type type = new EmployeeLeaveTransfer.Leavesetting.Type();
		type.setId(leave.getType().getId());
		type.setName(leave.getType().getName());
		leavesetting.setType(type);
		ret.setLeavesetting(leavesetting);
		return ret;
	}

}
