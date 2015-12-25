package service;

import java.util.Collection;

import transfer.AttendRecordTransfer;
import entity.AttendRecord;

public interface AttendRecordService {

	public AttendRecordTransfer retrieve(long id);

	public void delete(long id);

	public AttendRecordTransfer save(AttendRecord attendrecord);

	public Collection<AttendRecordTransfer> findAll();

	public AttendRecordTransfer update(long id, AttendRecord attendrecord);

}