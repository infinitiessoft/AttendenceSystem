package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * In this example we will use Employee as entity.Id, firstname, lastname,
 * birthdate, cellphone, jobtitle, username, createddate, email, password and
 * gender are the attributes of this entity. It contains default constructor,
 * setter and getter methods of those attributes.
 * 
 */

@Entity
@Table(name = "leavesetting")
public class Leavesetting extends AbstractEntity {
	private static final long serialVersionUID = 7711505597348200997L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "personal", nullable = false)
	private Long personal;
	
	@Column(name = "personalUsed", nullable = false)
	private Long personalUsed;

	@Column(name = "sick", nullable = false)
	private Long sick;
	
	@Column(name = "sickUsed", nullable = false)
	private Long sickUsed;

	@Column(name = "annual", nullable = false)
	private Long annual;
	
	@Column(name = "annualUsed", nullable = false)
	private Long annualUsed;
	
	@ManyToOne
	@JoinColumn(name = "employee")
	private Employee employee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPersonal() {
		return personal;
	}

	public void setPersonal(Long personal) {
		this.personal = personal;
	}

	public Long getSick() {
		return sick;
	}

	public void setSick(Long sick) {
		this.sick = sick;
	}

	public Long getAnnual() {
		return annual;
	}

	public void setAnnual(Long annual) {
		this.annual = annual;
	}

	public Long getPersonalUsed() {
		return personalUsed;
	}

	public void setPersonalUsed(Long personalUsed) {
		this.personalUsed = personalUsed;
	}

	public Long getSickUsed() {
		return sickUsed;
	}

	public void setSickUsed(Long sickUsed) {
		this.sickUsed = sickUsed;
	}

	public Long getAnnualUsed() {
		return annualUsed;
	}

	public void setAnnualUsed(Long annualUsed) {
		this.annualUsed = annualUsed;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Leavesetting() {
		super();
	}

}
