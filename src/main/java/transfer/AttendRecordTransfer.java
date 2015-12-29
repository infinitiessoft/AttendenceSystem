package transfer;

import java.util.Date;

public class AttendRecordTransfer {

	private long id;

	private Date modify_end_date;
	private Date modify_start_date;
	private Date modify_date;
	private Long employee_id;
	private String permit;
	private String reason;
	private Date duration;
	private Date end_date;
	private Date book_date;
	private Date start_date;
	private Long permic_person_id;
	private Date dateinterval;
	private Date endperiod;
	private Date modifyendperiod;
	private Date modifystartperiod;
	private String modifypermit;
	private Date startperiod;
	private Date period;
	private String permit2;
	private String modifypermit2;
	private String modifyreason;
	private String modifytype;
	private Long permic_person_id2;
	private Date modifydateinterval;

	public AttendRecordTransfer() {
		super();

	}

	public AttendRecordTransfer(long id, String name, Date modify_end_date, Date modify_start_date, Date modify_date,
			Long employee_id, String permit, String reason, Date duration, Date end_date, Date book_date,
			Date start_date, Long permic_person_id, Date dateinterval, Date endperiod, Date modifyendperiod,
			Date modifystartperiod, String modifypermit, Date startperiod, Date period, String permit2,
			String modifypermit2, String modifyreason, String modifytype, Long permic_person_id2,
			Date modifydateinterval) {

		super();
		this.id = id;

		this.modify_end_date = modify_end_date;
		this.modify_start_date = modify_start_date;
		this.modify_date = modify_date;
		this.employee_id = employee_id;
		this.permit = permit;
		this.reason = reason;
		this.duration = duration;
		this.end_date = end_date;
		this.book_date = book_date;
		this.start_date = start_date;
		this.permic_person_id = permic_person_id;
		this.dateinterval = dateinterval;
		this.endperiod = endperiod;
		this.modifyendperiod = modifyendperiod;
		this.modifystartperiod = modifystartperiod;
		this.modifypermit = modifypermit;
		this.startperiod = startperiod;
		this.period = period;
		this.permit2 = permit2;
		this.modifypermit2 = modifypermit2;
		this.modifyreason = modifyreason;
		this.modifytype = modifytype;
		this.permic_person_id2 = permic_person_id2;
		this.modifydateinterval = modifydateinterval;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getModify_end_date() {
		return modify_end_date;
	}

	public void setModify_end_date(Date modify_end_date) {
		this.modify_end_date = modify_end_date;
	}

	public Date getModify_start_date() {
		return modify_start_date;
	}

	public void setModify_start_date(Date modify_start_date) {
		this.modify_start_date = modify_start_date;
	}

	public Date getModify_date() {
		return modify_date;
	}

	public void setModify_date(Date modify_date) {
		this.modify_date = modify_date;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public String getPermit() {
		return permit;
	}

	public void setPermit(String permit) {
		this.permit = permit;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getDuration() {
		return duration;
	}

	public void setDuration(Date duration) {
		this.duration = duration;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getBook_date() {
		return book_date;
	}

	public void setBook_date(Date book_date) {
		this.book_date = book_date;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Long getPermic_person_id() {
		return permic_person_id;
	}

	public void setPermic_person_id(Long permic_person_id) {
		this.permic_person_id = permic_person_id;
	}

	public Date getDateinterval() {
		return dateinterval;
	}

	public void setDateinterval(Date dateinterval) {
		this.dateinterval = dateinterval;
	}

	public Date getEndperiod() {
		return endperiod;
	}

	public void setEndperiod(Date endperiod) {
		this.endperiod = endperiod;
	}

	public Date getModifyendperiod() {
		return modifyendperiod;
	}

	public void setModifyendperiod(Date modifyendperiod) {
		this.modifyendperiod = modifyendperiod;
	}

	public Date getModifystartperiod() {
		return modifystartperiod;
	}

	public void setModifystartperiod(Date modifystartperiod) {
		this.modifystartperiod = modifystartperiod;
	}

	public String getModifypermit() {
		return modifypermit;
	}

	public void setModifypermit(String modifypermit) {
		this.modifypermit = modifypermit;
	}

	public Date getStartperiod() {
		return startperiod;
	}

	public void setStartperiod(Date startperiod) {
		this.startperiod = startperiod;
	}

	public Date getPeriod() {
		return period;
	}

	public void setPeriod(Date period) {
		this.period = period;
	}

	public String getPermit2() {
		return permit2;
	}

	public void setPermit2(String permit2) {
		this.permit2 = permit2;
	}

	public String getModifypermit2() {
		return modifypermit2;
	}

	public void setModifypermit2(String modifypermit2) {
		this.modifypermit2 = modifypermit2;
	}

	public String getModifyreason() {
		return modifyreason;
	}

	public void setModifyreason(String modifyreason) {
		this.modifyreason = modifyreason;
	}

	public String getModifytype() {
		return modifytype;
	}

	public void setModifytype(String modifytype) {
		this.modifytype = modifytype;
	}

	public Long getPermic_person_id2() {
		return permic_person_id2;
	}

	public void setPermic_person_id2(Long permic_person_id2) {
		this.permic_person_id2 = permic_person_id2;
	}

	public void setModifydateinterval(Date modifydateinterval) {
		this.modifydateinterval = modifydateinterval;

	}

	public Date getModifydateinterval() {
		return modifydateinterval;
	}
}
