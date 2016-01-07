package entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "leave_setting")
public class Leavesetting extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "year", nullable = false)
	private Long year;

	@Column(name = "days", nullable = false)
	private Long days;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	private AttendRecordType type;

	@OneToOne(cascade = CascadeType.REMOVE, mappedBy = "leavesetting")
	private EmployeeLeave employeeLeave;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AttendRecordType getType() {
		return type;
	}

	public void setType(AttendRecordType type) {
		this.type = type;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.days = days;
	}

	@XmlTransient
	public EmployeeLeave getEmployeeLeave() {
		return employeeLeave;
	}

	public void setEmployeeLeave(EmployeeLeave employeeLeave) {
		this.employeeLeave = employeeLeave;
	}

}
