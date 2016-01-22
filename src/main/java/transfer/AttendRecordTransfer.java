package transfer;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonFormat;

import entity.AttendRecord;

public class AttendRecordTransfer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Status implements Serializable {
		pending, permit, reject,
	}

	public static class Employee implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Long id;
		private String name;

		private boolean isIdSet;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			isIdSet = true;
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@XmlTransient
		public boolean isIdSet() {
			return isIdSet;
		}

		@XmlTransient
		public void setIdSet(boolean isIdSet) {
			this.isIdSet = isIdSet;
		}

	}

	public static class Type implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Long id;
		private String name;

		private boolean isIdSet;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			isIdSet = true;
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@XmlTransient
		public boolean isIdSet() {
			return isIdSet;
		}

		@XmlTransient
		public void setIdSet(boolean isIdSet) {
			this.isIdSet = isIdSet;
		}

	}

	private Long id;
	private Employee applicant;
	private String reason;
	private Double duration;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date endDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date bookDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date startDate;
	private Type type;
	private Status status;

	private boolean isApplicantSet;
	private boolean isReasonSet;
	private boolean isDurationSet;
	private boolean isEndDateSet;
	private boolean isBookDateSet;
	private boolean isStartDateSet;
	private boolean isTypeSet;

	public AttendRecordTransfer() {
		super();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getApplicant() {
		return applicant;
	}

	public void setApplicant(Employee applicant) {
		this.isApplicantSet = true;
		this.applicant = applicant;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.isReasonSet = true;
		this.reason = reason;
	}

	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.isDurationSet = true;
		this.duration = duration;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.isEndDateSet = true;
		this.endDate = endDate;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.isBookDateSet = true;
		this.bookDate = bookDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.isStartDateSet = true;
		this.startDate = startDate;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.isTypeSet = true;
		this.type = type;
	}

	@XmlTransient
	public boolean isEmployeeSet() {
		return isApplicantSet;
	}

	@XmlTransient
	public void setEmployeeSet(boolean isEmployeeSet) {
		this.isApplicantSet = isEmployeeSet;
	}

	@XmlTransient
	public boolean isReasonSet() {
		return isReasonSet;
	}

	@XmlTransient
	public void setReasonSet(boolean isReasonSet) {
		this.isReasonSet = isReasonSet;
	}

	@XmlTransient
	public boolean isDurationSet() {
		return isDurationSet;
	}

	@XmlTransient
	public void setDurationSet(boolean isDurationSet) {
		this.isDurationSet = isDurationSet;
	}

	@XmlTransient
	public boolean isEndDateSet() {
		return isEndDateSet;
	}

	@XmlTransient
	public void setEndDateSet(boolean isEndDateSet) {
		this.isEndDateSet = isEndDateSet;
	}

	@XmlTransient
	public boolean isBookDateSet() {
		return isBookDateSet;
	}

	@XmlTransient
	public void setBookDateSet(boolean isBookDateSet) {
		this.isBookDateSet = isBookDateSet;
	}

	@XmlTransient
	public boolean isStartDateSet() {
		return isStartDateSet;
	}

	@XmlTransient
	public void setStartDateSet(boolean isStartDateSet) {
		this.isStartDateSet = isStartDateSet;
	}

	@XmlTransient
	public boolean isTypeSet() {
		return isTypeSet;
	}

	@XmlTransient
	public void setTypeSet(boolean isTypeSet) {
		this.isTypeSet = isTypeSet;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@XmlTransient
	public static AttendRecordTransfer toAttendRecordTransfer(
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
		ret.setApplicant(employee);
		ret.setReason(attendRecord.getReason());
		ret.setStartDate(attendRecord.getStartDate());
		ret.setEndDate(attendRecord.getEndDate());
		ret.setStatus(Status.valueOf(attendRecord.getStatus()));

		return ret;
	}

}
