package transfer;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonFormat;

public class DurationTransfer {

	private Double days;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date endDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Date startDate;

	private boolean isEndDateSet;
	private boolean isStartDateSet;

	public DurationTransfer() {
		super();
	}

	public Double getDays() {
		return days;
	}

	public void setDays(Double days) {
		this.days = days;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.isEndDateSet = true;
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.isStartDateSet = true;
		this.startDate = startDate;
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
	public boolean isStartDateSet() {
		return isStartDateSet;
	}

	@XmlTransient
	public void setStartDateSet(boolean isStartDateSet) {
		this.isStartDateSet = isStartDateSet;
	}
}
