package transfer;

public class DepartmentTransfer {

	private long id;
	private String name;
	private long manager_id;
	private long responseto;

	// private List<String, Boolean> departments;

	public DepartmentTransfer() {
		super();

	}

	public DepartmentTransfer(long id, String name, long manager_id,
			long responseto) {
		super();
		this.id = id;
		this.name = name;
		this.manager_id = manager_id;
		this.responseto = responseto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getManager_id() {
		return manager_id;
	}

	public void setManager_id(long manager_id) {
		this.manager_id = manager_id;
	}

	public long getResponseto() {
		return responseto;
	}

	public void setResponseto(long responseto) {
		this.responseto = responseto;
	}

}
