package dao;

public interface AttendRecordDao extends Dao<AttendRecord, Long> {

	Object findByName(String username);

}
