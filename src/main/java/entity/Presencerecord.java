package entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "presencerecord")

public class Presencerecord implements Serializable {
	private static final long serialVersionUID = 7711505597348200997L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "attenddate", nullable = false)
	private Date attenddate;

	@Column(name = "bookdate", nullable = false)
	private Date bookdate;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "book_person_id", nullable = false)
	private Long book_person_id;

	@Column(name = "employee_id", nullable = false)
	private Long employee_id;

	@ManyToOne
	@JoinColumn(name = "employee")
	private Employee employee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getAttenddate() {
		return attenddate;
	}

	public void setAttenddate(Date attenddate) {
		this.attenddate = attenddate;
	}

	public Date getBookdate() {
		return bookdate;
	}

	public void setBookdate(Date bookdate) {
		this.bookdate = bookdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getBook_person_id() {
		return book_person_id;
	}

	public void setBook_person_id(Long book_person_id) {
		this.book_person_id = book_person_id;
	}

	public Long getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(Long employee_id) {
		this.employee_id = employee_id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book_person_id == null) ? 0 : book_person_id.hashCode());
		result = prime * result + ((employee == null) ? 0 : employee.hashCode());
		result = prime * result + ((employee_id == null) ? 0 : employee_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Presencerecord other = (Presencerecord) obj;
		if (book_person_id == null) {
			if (other.book_person_id != null)
				return false;
		} else if (!book_person_id.equals(other.book_person_id))
			return false;
		if (employee == null) {
			if (other.employee != null)
				return false;
		} else if (!employee.equals(other.employee))
			return false;
		if (employee_id == null) {
			if (other.employee_id != null)
				return false;
		} else if (!employee_id.equals(other.employee_id))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	public Presencerecord(Long id, Date attenddate, Date bookdate, String name, String status, Long book_person_id,
			Long employee_id, Employee employee) {
		super();
		this.id = id;
		this.attenddate = attenddate;
		this.bookdate = bookdate;
		this.name = name;
		this.status = status;
		this.book_person_id = book_person_id;
		this.employee_id = employee_id;
		this.employee = employee;
	}

	public Presencerecord() {
		super();
		// TODO Auto-generated constructor stub
	}
}