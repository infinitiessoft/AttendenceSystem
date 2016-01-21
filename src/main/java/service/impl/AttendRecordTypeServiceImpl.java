package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import service.AttendRecordTypeService;
import transfer.AttendRecordTypeTransfer;
import dao.AttendRecordTypeDao;
import entity.AttendRecordType;
import exceptions.AttendRecordTypeNotFoundException;
import exceptions.RemovingIntegrityViolationException;

public class AttendRecordTypeServiceImpl implements AttendRecordTypeService {

	private AttendRecordTypeDao attendRecordTypeDao;

	public AttendRecordTypeServiceImpl(AttendRecordTypeDao attendRecordTypeDao) {
		this.attendRecordTypeDao = attendRecordTypeDao;
	}

	@Override
	public AttendRecordTypeTransfer retrieve(long id) {
		AttendRecordType attendRecordType = attendRecordTypeDao.findOne(id);
		if (attendRecordType == null) {
			throw new AttendRecordTypeNotFoundException(id);
		}
		return toAttendRecordTypeTransfer(attendRecordType);
	}

	@Override
	public void delete(long id) {
		try {
			attendRecordTypeDao.delete(id);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			throw new RemovingIntegrityViolationException(
					AttendRecordType.class);
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			throw new AttendRecordTypeNotFoundException(id);
		}
	}

	@Override
	public AttendRecordTypeTransfer save(
			AttendRecordTypeTransfer attendRecordType) {
		attendRecordType.setId(null);
		AttendRecordType dep = new AttendRecordType();
		setUpAttendRecordType(attendRecordType, dep);
		return toAttendRecordTypeTransfer(attendRecordTypeDao.save(dep));
	}

	private void setUpAttendRecordType(AttendRecordTypeTransfer transfer,
			AttendRecordType dep) {
		if (transfer.isNameSet()) {
			dep.setName(transfer.getName());
		}
	}

	@Override
	public AttendRecordTypeTransfer update(long id,
			AttendRecordTypeTransfer updated) {
		AttendRecordType attendRecordType = attendRecordTypeDao.findOne(id);
		if (attendRecordType == null) {
			throw new AttendRecordTypeNotFoundException(id);
		}
		setUpAttendRecordType(updated, attendRecordType);
		return toAttendRecordTypeTransfer(attendRecordTypeDao
				.save(attendRecordType));
	}

	@Override
	public Page<AttendRecordTypeTransfer> findAll(
			Specification<AttendRecordType> spec, Pageable pageable) {
		List<AttendRecordTypeTransfer> transfers = new ArrayList<AttendRecordTypeTransfer>();
		Page<AttendRecordType> attendRecordTypes = attendRecordTypeDao.findAll(
				spec, pageable);
		for (AttendRecordType attendRecordType : attendRecordTypes) {
			transfers.add(toAttendRecordTypeTransfer(attendRecordType));
		}
		Page<AttendRecordTypeTransfer> rets = new PageImpl<AttendRecordTypeTransfer>(
				transfers, pageable, attendRecordTypes.getTotalElements());
		return rets;
	}

	private AttendRecordTypeTransfer toAttendRecordTypeTransfer(
			AttendRecordType attendRecordType) {
		AttendRecordTypeTransfer ret = new AttendRecordTypeTransfer();
		ret.setId(attendRecordType.getId());
		ret.setName(attendRecordType.getName());
		return ret;
	}

}