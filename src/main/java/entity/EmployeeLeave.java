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
@Table(name = "employee_leave")
public class EmployeeLeave extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", nullable = false)
	private Employee employee;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "leavesetting_id", nullable = false)
	private Leavesetting leavesetting;
	@Column(name = "used_days", nullable = false)
	private Long usedDays;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Leavesetting getLeavesetting() {
		return leavesetting;
	}

	public void setLeavesetting(Leavesetting leavesetting) {
		this.leavesetting = leavesetting;
	}

	public Long getUsedDays() {
		return usedDays;
	}

	public void setUsedDays(Long usedDays) {
		this.usedDays = usedDays;
	}

}
