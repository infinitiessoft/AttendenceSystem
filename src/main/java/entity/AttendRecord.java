//package entity;
//
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "attend_record")
//public class AttendRecord extends AbstractEntity {
//	private static final long serialVersionUID = 7711505597348200997L;
//
//	@Id
//	@Column(name = "id")
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	private Long id;
//
//	@Column(name = "end_date", nullable = false)
//	private Date end_date;
//
//	@Column(name = "reason", nullable = false)
//	private String reason;
//
//	@Column(name = "employee_id", nullable = false)
//	private Long employee_id;
//
//	@Column(name = "book_date", nullable = false)
//	private Date book_date;
//
//	@Column(name = "start_date", nullable = false)
//	private Date start_date;
//
//	@Column(name = "endperiod", nullable = false)
//	private Date endperiod;
//	
//	private Type type;
//
//
//	public AttendRecord(Long id, Date end_date, Date duration, String reason, String permit, Long employee_id,
//			Date modify_date, Date modify_start_date, Date modify_end_date, Date book_date, Date start_date,
//			Long permic_person_id, Date dateinterval, Date endperiod, Date modifyendperiod, Date modifystartperiod,
//			String modifypermit, Date period, Date startperiod, Date modifydateinterval, String permit2,
//			Long permic_person_id2, String modifypermit2, String modifyreason, String modifytype,
//			Set<AttendRecord> records) {
//		super();
//		this.id = id;
//		this.end_date = end_date;
//		this.duration = duration;
//		this.reason = reason;
//		this.permit = permit;
//		this.employee_id = employee_id;
//		this.modify_date = modify_date;
//		this.modify_start_date = modify_start_date;
//		this.modify_end_date = modify_end_date;
//		this.book_date = book_date;
//		this.start_date = start_date;
//		this.permic_person_id = permic_person_id;
//		this.dateinterval = dateinterval;
//		this.endperiod = endperiod;
//		this.modifyendperiod = modifyendperiod;
//		this.modifystartperiod = modifystartperiod;
//		this.modifypermit = modifypermit;
//		this.period = period;
//		this.startperiod = startperiod;
//		this.modifydateinterval = modifydateinterval;
//		this.permit2 = permit2;
//		this.permic_person_id2 = permic_person_id2;
//		this.modifypermit2 = modifypermit2;
//		this.modifyreason = modifyreason;
//		this.modifytype = modifytype;
//
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Date getEnd_date() {
//		return end_date;
//	}
//
//	public void setEnd_date(Date end_date) {
//		this.end_date = end_date;
//	}
//
//	public Date getDuration() {
//		return duration;
//	}
//
//	public void setDuration(Date duration) {
//		this.duration = duration;
//	}
//
//	public String getReason() {
//		return reason;
//	}
//
//	public void setReason(String reason) {
//		this.reason = reason;
//	}
//
//	public String getPermit() {
//		return permit;
//	}
//
//	public void setPermit(String permit) {
//		this.permit = permit;
//	}
//
//	public Long getEmployee_id() {
//		return employee_id;
//	}
//
//	public void setEmployee_id(Long employee_id) {
//		this.employee_id = employee_id;
//	}
//
//	public Date getModify_date() {
//		return modify_date;
//	}
//
//	public void setModify_date(Date modify_date) {
//		this.modify_date = modify_date;
//	}
//
//	public Date getModify_start_date() {
//		return modify_start_date;
//	}
//
//	public void setModify_start_date(Date modify_start_date) {
//		this.modify_start_date = modify_start_date;
//	}
//
//	public Date getModify_end_date() {
//		return modify_end_date;
//	}
//
//	public void setModify_end_date(Date modify_end_date) {
//		this.modify_end_date = modify_end_date;
//	}
//
//	public Date getBook_date() {
//		return book_date;
//	}
//
//	public void setBook_date(Date book_date) {
//		this.book_date = book_date;
//	}
//
//	public Date getStart_date() {
//		return start_date;
//	}
//
//	public void setStart_date(Date start_date) {
//		this.start_date = start_date;
//	}
//
//	public Long getPermic_person_id() {
//		return permic_person_id;
//	}
//
//	public void setPermic_person_id(Long permic_person_id) {
//		this.permic_person_id = permic_person_id;
//	}
//
//	public Date getDateinterval() {
//		return dateinterval;
//	}
//
//	public void setDateinterval(Date dateinterval) {
//		this.dateinterval = dateinterval;
//	}
//
//	public Date getEndperiod() {
//		return endperiod;
//	}
//
//	public void setEndperiod(Date endperiod) {
//		this.endperiod = endperiod;
//	}
//
//	public Date getModifyendperiod() {
//		return modifyendperiod;
//	}
//
//	public void setModifyendperiod(Date modifyendperiod) {
//		this.modifyendperiod = modifyendperiod;
//	}
//
//	public Date getModifystartperiod() {
//		return modifystartperiod;
//	}
//
//	public void setModifystartperiod(Date modifystartperiod) {
//		this.modifystartperiod = modifystartperiod;
//	}
//
//	public String getModifypermit() {
//		return modifypermit;
//	}
//
//	public void setModifypermit(String modifypermit) {
//		this.modifypermit = modifypermit;
//	}
//
//	public Date getPeriod() {
//		return period;
//	}
//
//	public void setPeriod(Date period) {
//		this.period = period;
//	}
//
//	public Date getStartperiod() {
//		return startperiod;
//	}
//
//	public void setStartperiod(Date startperiod) {
//		this.startperiod = startperiod;
//	}
//
//	public Date getModifydateinterval() {
//		return modifydateinterval;
//	}
//
//	public void setModifydateinterval(Date modifydateinterval) {
//		this.modifydateinterval = modifydateinterval;
//	}
//
//	public String getPermit2() {
//		return permit2;
//	}
//
//	public void setPermit2(String permit2) {
//		this.permit2 = permit2;
//	}
//
//	public Long getPermic_person_id2() {
//		return permic_person_id2;
//	}
//
//	public void setPermic_person_id2(Long permic_person_id2) {
//		this.permic_person_id2 = permic_person_id2;
//	}
//
//	public String getModifypermit2() {
//		return modifypermit2;
//	}
//
//	public void setModifypermit2(String modifypermit2) {
//		this.modifypermit2 = modifypermit2;
//	}
//
//	public String getModifyreason() {
//		return modifyreason;
//	}
//
//	public void setModifyreason(String modifyreason) {
//		this.modifyreason = modifyreason;
//	}
//
//	public String getModifytype() {
//		return modifytype;
//	}
//
//	public void setModifytype(String modifytype) {
//		this.modifytype = modifytype;
//	}
//
//	@Column(name = "modifypermit2", nullable = false)
//	private String modifypermit2;
//
//	@Column(name = "modifyreason", nullable = false)
//	private String modifyreason;
//
//	@Column(name = "modifytype", nullable = false)
//	private String modifytype;
//
//	public AttendRecord() {
//		super();
//
//	}
//
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee", cascade = CascadeType.REMOVE)
//	private Set<AttendRecord> records = new HashSet<AttendRecord>(0);
//
//}
