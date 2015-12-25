package transfer;

import java.util.Date;

public class PresencerecordTransfer {

	private Long id;
	private Date attenddate;
	private Date bookdate;
	private String name;
	private String status;
	private Long book_person_id;
	private Long employee_id;

	// private List<Employee> employee;
	public PresencerecordTransfer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PresencerecordTransfer(Long id, Date attenddate, Date bookdate, String name, String status,
			Long book_person_id, Long employee_id) {
		super();
		this.id = id;
		this.attenddate = attenddate;
		this.bookdate = bookdate;
		this.name = name;
		this.status = status;
		this.book_person_id = book_person_id;
		this.employee_id = employee_id;
	}

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

}
