package entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "employee")
public class Employee extends AbstractEntity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1154129853752708026L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "responseto")
	private Employee employee;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "department_id", nullable = false)
	private Department department;
	@Column(name = "name", nullable = false, length = 20)
	private String name;
	@Temporal(TemporalType.DATE)
	@Column(name = "dateofjoined", nullable = false, length = 13)
	private Date dateofjoined;
	@Column(name = "password", nullable = false, length = 100)
	private String password;
	@Column(name = "username", unique = true, nullable = false, length = 20)
	private String username;
	@Column(name = "email", unique = true, nullable = false, length = 40)
	private String email;
	@Column(name = "gender", nullable = false, length = 6)
	private String gender;
	@Column(name = "comment")
	private String comment;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employee")
	private Set<Employee> employees = new HashSet<Employee>(0);
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeByPermicPersonId")
	// private Set<AttendRecord> attendRecordsForPermicPersonId = new
	// HashSet<AttendRecord>(
	// 0);
	// @OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeByEmployeeId")
	// private Set<AttendRecord> attendRecordsForEmployeeId = new
	// HashSet<AttendRecord>(
	// 0);
	@Column(name = "color")
	private String color;

	@Column(name = "specialleave")
	private float specialleave;

	@Column(name = "specialleave_used")
	private float specialleave_used;

	@Column(name = "sick")
	private float sick;

	@Column(name = "sick_used")
	private float sick_used;

	@Column(name = "personal")
	private float personal;

	@Column(name = "personal_used")
	private float personal_used;

	@Column(name = "official_used")
	private float official_used;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "employee")
	private Set<EmployeeRole> employeeRoles = new HashSet<EmployeeRole>(0);

	@Column(name = "lastlogin")
	private Date lastLogin;

	@OneToMany(mappedBy = "employee")
	private List<Leavesetting> leavesettings;

	@Override
	@Transient
	@XmlTransient
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<EmployeeRole> roles = this.getEmployeeRoles();
		if (roles == null) {
			return Collections.emptyList();
		}

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (EmployeeRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole().getName()));
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	@Transient
	@XmlTransient
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@Transient
	@XmlTransient
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@Transient
	@XmlTransient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@Transient
	@XmlTransient
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@XmlTransient
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateofjoined() {
		return dateofjoined;
	}

	public void setDateofjoined(Date dateofjoined) {
		this.dateofjoined = dateofjoined;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	// public Set<Employee> getEmployees() {
	// return employees;
	// }
	//
	// public void setEmployees(Set<Employee> employees) {
	// this.employees = employees;
	// }

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public float getSpecialleave() {
		return specialleave;
	}

	public void setSpecialleave(float specialleave) {
		this.specialleave = specialleave;
	}

	public float getSpecialleave_used() {
		return specialleave_used;
	}

	public void setSpecialleave_used(float specialleave_used) {
		this.specialleave_used = specialleave_used;
	}

	public float getSick() {
		return sick;
	}

	public void setSick(float sick) {
		this.sick = sick;
	}

	public float getSick_used() {
		return sick_used;
	}

	public void setSick_used(float sick_used) {
		this.sick_used = sick_used;
	}

	public float getPersonal() {
		return personal;
	}

	public void setPersonal(float personal) {
		this.personal = personal;
	}

	public float getPersonal_used() {
		return personal_used;
	}

	public void setPersonal_used(float personal_used) {
		this.personal_used = personal_used;
	}

	public float getOfficial_used() {
		return official_used;
	}

	public void setOfficial_used(float official_used) {
		this.official_used = official_used;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@XmlTransient
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@XmlTransient
	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	@XmlTransient
	public List<Leavesetting> getLeavesettings() {
		return leavesettings;
	}

	public void setLeavesettings(List<Leavesetting> leavesettings) {
		this.leavesettings = leavesettings;
	}

	@XmlTransient
	public Set<EmployeeRole> getEmployeeRoles() {
		return employeeRoles;
	}

	public void setEmployeeRoles(Set<EmployeeRole> employeeRoles) {
		this.employeeRoles = employeeRoles;
	}

}
