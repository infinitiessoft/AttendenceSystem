package transfer;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import transfer.AttendRecordTransfer.Status;

import com.fasterxml.jackson.annotation.JsonFormat;

import entity.AttendRecord;

public class PermittedAttendRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long employeeId;
	private String employeeName;
	private String reason;
	private Double duration;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Date endDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Date bookDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private Date startDate;
	private String type;
	private Status status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	@XmlTransient
	public static PermittedAttendRecord toPermittedAttendRecord(
			AttendRecord attendRecord) {
		PermittedAttendRecord ret = new PermittedAttendRecord();
		ret.setId(attendRecord.getId());
		ret.setType(attendRecord.getType().getName());
		ret.setBookDate(attendRecord.getBookDate());
		ret.setDuration(attendRecord.getDuration());
		ret.setEmployeeId(attendRecord.getEmployee().getId());
		ret.setEmployeeName(attendRecord.getEmployee().getName());
		ret.setReason(attendRecord.getReason());
		ret.setStartDate(attendRecord.getStartDate());
		ret.setEndDate(attendRecord.getEndDate());
		ret.setStatus(Status.valueOf(attendRecord.getStatus()));

		return ret;
	}

}
