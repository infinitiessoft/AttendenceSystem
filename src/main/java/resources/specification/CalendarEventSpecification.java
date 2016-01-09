package resources.specification;

import java.util.Date;

public class CalendarEventSpecification {

	private Date timeMin;
	private Date timeMax;

	public Date getTimeMin() {
		return timeMin;
	}

	public void setTimeMin(Date timeMin) {
		this.timeMin = timeMin;
	}

	public Date getTimeMax() {
		return timeMax;
	}

	public void setTimeMax(Date timeMax) {
		this.timeMax = timeMax;
	}

}
