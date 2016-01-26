package transfer;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import transfer.AttendRecordTransfer.Status;
import transfer.AttendRecordTransfer.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EventTransfer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static enum Action {
		permit, reject;
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

	public static class AttendRecord implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Long id;
		private Employee employee;
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
		private boolean isIdSet;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			setIdSet(true);
			this.id = id;
		}

		public Employee getEmployee() {
			return employee;
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}

		public String getReason() {
			return reason;
		}

		public void setReason(String reason) {
			this.reason = reason;
		}

		public Double getDuration() {
			return duration;
		}

		public void setDuration(Double duration) {
			this.duration = duration;
		}

		public Date getEndDate() {
			return endDate;
		}

		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}

		public Date getBookDate() {
			return bookDate;
		}

		public void setBookDate(Date bookDate) {
			this.bookDate = bookDate;
		}

		public Date getStartDate() {
			return startDate;
		}

		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}

		public Status getStatus() {
			return status;
		}

		public void setStatus(Status status) {
			this.status = status;
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date bookDate;
	private String action;
	private Employee approver;
	private AttendRecord record;
	private boolean isActionSet;
	private boolean isApproverSet;
	private boolean isRecordSet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		isActionSet = true;
		this.action = action;
	}

	public AttendRecord getRecord() {
		return record;
	}

	public void setRecord(AttendRecord record) {
		if (record != null) {
			isRecordSet = true;
		}
		this.record = record;
	}

	@XmlTransient
	public boolean isActionSet() {
		return isActionSet;
	}

	@XmlTransient
	public void setActionSet(boolean isActionSet) {
		this.isActionSet = isActionSet;
	}

	public Employee getApprover() {
		return approver;
	}

	public void setApprover(Employee approver) {
		if (approver != null) {
			isApproverSet = true;
		}
		this.approver = approver;
	}

	@XmlTransient
	public boolean isApproverSet() {
		return isApproverSet;
	}

	@XmlTransient
	public void setApproverSet(boolean isApproverSet) {
		this.isApproverSet = isApproverSet;
	}

	@XmlTransient
	public boolean isRecordSet() {
		return isRecordSet;
	}

	@XmlTransient
	public void setRecordSet(boolean isRecordSet) {
		this.isRecordSet = isRecordSet;
	}

}
