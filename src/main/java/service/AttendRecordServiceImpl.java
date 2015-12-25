package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dao.AttendRecordDao;
import transfer.AttendRecordTransfer;

public class AttendRecordServiceImpl implements AttendRecordService {

	private AttendRecordDao attendrecordDao;

	public AttendRecordServiceImpl(AttendRecordDao attendrecordDao) {
		this.attendrecordDao = attendrecordDao;
	}

	@Override
	public AttendRecordTransfer retrieve(long id) {
		return toAttendRecordTransfer(attendrecordDao.find(id));
	}

	@Override
	public void delete(long id) {
		attendrecordDao.delete(id);
	}

	@Override
	public AttendRecordTransfer save(AttendRecord attendrecord) {
		return toAttendRecordTransfer(attendrecordDao.save(attendrecord));
	}

	@Override
	public AttendRecordTransfer update(long id, AttendRecord attendrecord) {
		return toAttendRecordTransfer(attendrecordDao.save(attendrecord));
	}

	@Override
	public Collection<AttendRecordTransfer> findAll() {
		List<AttendRecordTransfer> rets = new ArrayList<AttendRecordTransfer>();

		for (AttendRecord attendrecord : attendrecordDao.findAll()) {
			rets.add(toAttendRecordTransfer(attendrecord));
		}
		return rets;
	}

	@Override
	public AttendRecordTransfer findByUsername(String username) {
		return toAttendRecordTransfer(attendrecordDao.findByName(username));
	}
}