package service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import resources.specification.AttendRecordSpecification;
import transfer.AttendRecordTransfer;
import dao.AttendRecordDao;
import dao.AttendRecordTypeDao;
import dao.EmployeeDao;
import entity.AttendRecord;
import exceptions.AttendRecordNotFoundException;
import exceptions.AttendRecordTypeNotFoundException;
import exceptions.EmployeeNotFoundException;

public class AttendRecordServiceImpl implements AttendRecordService {

	private AttendRecordDao attendRecordDao;
	private AttendRecordTypeDao attendRecordTypeDao;
	private EmployeeDao employeeDao;

	public AttendRecordServiceImpl(AttendRecordDao attendRecordDao,
			AttendRecordTypeDao attendRecordTypeDao, EmployeeDao employeeDao) {
		this.attendRecordDao = attendRecordDao;
		this.attendRecordTypeDao = attendRecordTypeDao;
		this.employeeDao = employeeDao;
	}

	@Override
	public AttendRecordTransfer retrieve(long id) {
		AttendRecord record = attendRecordDao.findOne(id);
		if (record == null) {
			throw new AttendRecordNotFoundException(id);
		}
		return toAttendRecordTransfer(record);
	}

	@Override
	public void delete(long id) {
		try {
			attendRecordDao.delete(id);
		} catch (NullPointerException e) {
			throw new AttendRecordNotFoundException(id);
		}
	}

	@Override
	public AttendRecordTransfer save(AttendRecordTransfer attendRecord) {
		attendRecord.setId(null);
		AttendRecord dep = new AttendRecord();
		setUpAttendRecord(attendRecord, dep);
		return toAttendRecordTransfer(attendRecordDao.save(dep));
	}

	@Override
	public AttendRecordTransfer update(long id, AttendRecordTransfer updated) {
		AttendRecord attendRecord = attendRecordDao.findOne(id);
		if (attendRecord == null) {
			throw new AttendRecordNotFoundException(id);
		}
		setUpAttendRecord(updated, attendRecord);
		return toAttendRecordTransfer(attendRecordDao.save(attendRecord));
	}

	@Override
	public Page<AttendRecordTransfer> findAll(AttendRecordSpecification spec,
			Pageable pageable) {
		List<AttendRecordTransfer> transfers = new ArrayList<AttendRecordTransfer>();
		Page<AttendRecord> attendRecords = attendRecordDao.findAll(spec,
				pageable);
		for (AttendRecord attendRecord : attendRecords) {
			transfers.add(toAttendRecordTransfer(attendRecord));
		}
		Page<AttendRecordTransfer> rets = new PageImpl<AttendRecordTransfer>(
				transfers, pageable, attendRecords.getTotalElements());
		return rets;
	}

	private void setUpAttendRecord(AttendRecordTransfer transfer,
			AttendRecord newEntry) {
		if (transfer.isStartDateSet()) {
			newEntry.setStartDate(transfer.getStartDate());
		}
		if (transfer.isEndDateSet()) {
			newEntry.setEndDate(transfer.getEndDate());
		}
		if (transfer.isBookDateSet()) {
			newEntry.setBookDate(transfer.getBookDate());
		}
		if (transfer.isReasonSet()) {
			newEntry.setReason(transfer.getReason());
		}
		if (transfer.isDurationSet()) {
			newEntry.setDuration(transfer.getDuration());
		}
		if (transfer.isTypeSet()) {
			if (transfer.getType().isIdSet()) {
				entity.AttendRecordType type = attendRecordTypeDao
						.findOne(transfer.getType().getId());
				if (type == null) {
					throw new AttendRecordTypeNotFoundException(transfer
							.getType().getId());
				}
				newEntry.setType(type);
			}
		}
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
	}

	private AttendRecordTransfer toAttendRecordTransfer(
			AttendRecord attendRecord) {
		AttendRecordTransfer ret = new AttendRecordTransfer();
		ret.setId(attendRecord.getId());
		AttendRecordTransfer.Employee employee = new AttendRecordTransfer.Employee();
		employee.setId(attendRecord.getEmployee().getId());
		employee.setName(attendRecord.getEmployee().getName());
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(attendRecord.getType().getId());
		type.setName(attendRecord.getType().getName());
		ret.setType(type);
		ret.setBookDate(attendRecord.getBookDate());
		ret.setDuration(attendRecord.getDuration());
		ret.setEmployee(employee);
		ret.setReason(attendRecord.getReason());
		ret.setStartDate(attendRecord.getStartDate());
		ret.setEndDate(attendRecord.getEndDate());

		return ret;

	}

}