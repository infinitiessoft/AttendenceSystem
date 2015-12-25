package transfer;

public class JobTransfer {

	private Long id;
	private String name;
	private Long department_id;
	private Long job_type_id;
	// private List<String, Boolean> job_type;

	public JobTransfer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JobTransfer(Long id, String name, Long department_id, Long job_type_id) {
		super();
		this.id = id;
		this.name = name;
		this.department_id = department_id;
		this.job_type_id = job_type_id;
	}

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

	public Long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}

	public Long getJob_type_id() {
		return job_type_id;
	}

	public void setJob_type_id(Long job_type_id) {
		this.job_type_id = job_type_id;
	}

}
