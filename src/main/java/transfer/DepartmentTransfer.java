package transfer;

import javax.xml.bind.annotation.XmlTransient;

public class DepartmentTransfer {

	private Long id;
	private String name;
	private Long manager_id;
	private Long responseto;

	private boolean isNameSet;

	public DepartmentTransfer() {
		super();

	}

	public DepartmentTransfer(Long id, String name, Long manager_id,
			Long responseto) {
		super();
		this.id = id;
		this.name = name;
		this.manager_id = manager_id;
		this.responseto = responseto;
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
		setNameSet(true);
		this.name = name;
	}

	public Long getManager_id() {
		return manager_id;
	}

	public void setManager_id(Long manager_id) {
		this.manager_id = manager_id;
	}

	public Long getResponseto() {
		return responseto;
	}

	public void setResponseto(Long responseto) {
		this.responseto = responseto;
	}

	@XmlTransient
	public boolean isNameSet() {
		return isNameSet;
	}

	@XmlTransient
	public void setNameSet(boolean isNameSet) {
		this.isNameSet = isNameSet;
	}

}
