package entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "leave_setting", uniqueConstraints = @UniqueConstraint(columnNames = {
		"year", "type_id" }))
public class Leavesetting extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "year", nullable = false)
	private Long year;

	@Column(name = "days", nullable = false)
	private Double days;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	private AttendRecordType type;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "leavesetting", cascade = CascadeType.REMOVE)
	private Set<EmployeeLeave> employeeLeaves = new HashSet<EmployeeLeave>(0);

	@Column(name = "name")
	private String name;

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

	public Double getDays() {
		return days;
	}

	public void setDays(Double days) {
		this.days = days;
	}

	public Set<EmployeeLeave> getEmployeeLeaves() {
		return employeeLeaves;
	}

	public void setEmployeeLeaves(Set<EmployeeLeave> employeeLeaves) {
		this.employeeLeaves = employeeLeaves;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = type.getName() + "_" + year + "_" + days;
	}

}
