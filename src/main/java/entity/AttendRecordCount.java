package entity;

public class AttendRecordCount {
	private Long count;
	private AttendRecordType type;

	public AttendRecordCount() {

	}

	public AttendRecordCount(Long count, AttendRecordType type) {
		super();
		this.count = count;
		this.type = type;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public AttendRecordType getType() {
		return type;
	}

	public void setType(AttendRecordType type) {
		this.type = type;
	}

}
