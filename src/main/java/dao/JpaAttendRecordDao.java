package dao;

import entity.AttendRecord;

public class JpaAttendRecordDao extends JpaDao<AttendRecord, Long> implements
		AttendRecordDao {

	public JpaAttendRecordDao() {
		super(AttendRecord.class);
	}

}
