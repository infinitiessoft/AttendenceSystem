package transfer;

import javax.xml.bind.annotation.XmlTransient;

public class AttendRecordTypeTransfer {

	private Long id;
	private String name;

	private boolean isNameSet;

	public AttendRecordTypeTransfer() {
		super();
	}

	public AttendRecordTypeTransfer(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@XmlTransient
	public boolean isNameSet() {
		return isNameSet;
	}

	@XmlTransient
	public void setNameSet(boolean isNameSet) {
		this.isNameSet = isNameSet;
	}

}
