package dao;

import entity.AttendRecord;

public interface AttendRecordDao extends Dao<AttendRecord, Long> {

	Object findByName(String username);

}
