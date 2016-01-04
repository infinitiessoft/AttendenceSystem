package transfer;

import javax.xml.bind.annotation.XmlTransient;

public class LeavesettingTransfer {

	private Long id;
	private String name;
	private Long personal;
	private Long sick;
	private Long annual;
	private Long personalUsed;
	private Long sickUsed;
	private Long annualUsed;

	private boolean isNameSet;
	private boolean isPersonalSet;
	private boolean isSickSet;
	private boolean isAnnualSet;
	private boolean isPersonalUsedSet;
	private boolean isSickUsedSet;
	private boolean isAnnualUsedSet;

	public LeavesettingTransfer() {
		super();
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
		isNameSet = true;
		this.name = name;
	}

	public Long getPersonal() {
		return personal;
	}

	public void setPersonal(Long personal) {
		isPersonalSet = true;
		this.personal = personal;
	}

	public Long getSick() {
		return sick;
	}

	public void setSick(Long sick) {
		isSickSet = true;
		this.sick = sick;
	}

	public Long getAnnual() {
		return annual;
	}

	public void setAnnual(Long annual) {
		isAnnualSet = true;
		this.annual = annual;
	}

	public Long getPersonalUsed() {
		return personalUsed;
	}

	public void setPersonalUsed(Long personalUsed) {
		isPersonalUsedSet = true;
		this.personalUsed = personalUsed;
	}

	public Long getSickUsed() {
		return sickUsed;
	}

	public void setSickUsed(Long sickUsed) {
		isSickUsedSet = true;
		this.sickUsed = sickUsed;
	}

	public Long getAnnualUsed() {
		return annualUsed;
	}

	public void setAnnualUsed(Long annualUsed) {
		isAnnualUsedSet = true;
		this.annualUsed = annualUsed;
	}

	@XmlTransient
	public boolean isNameSet() {
		return isNameSet;
	}

	public void setNameSet(boolean isNameSet) {
		this.isNameSet = isNameSet;
	}

	@XmlTransient
	public boolean isPersonalSet() {
		return isPersonalSet;
	}

	public void setPersonalSet(boolean isPersonalSet) {
		this.isPersonalSet = isPersonalSet;
	}

	@XmlTransient
	public boolean isSickSet() {
		return isSickSet;
	}

	public void setSickSet(boolean isSickSet) {
		this.isSickSet = isSickSet;
	}

	@XmlTransient
	public boolean isAnnualSet() {
		return isAnnualSet;
	}

	public void setAnnualSet(boolean isAnnualSet) {
		this.isAnnualSet = isAnnualSet;
	}

	@XmlTransient
	public boolean isPersonalUsedSet() {
		return isPersonalUsedSet;
	}

	public void setPersonalUsedSet(boolean isPersonalUsedSet) {
		this.isPersonalUsedSet = isPersonalUsedSet;
	}

	@XmlTransient
	public boolean isSickUsedSet() {
		return isSickUsedSet;
	}

	public void setSickUsedSet(boolean isSickUsedSet) {
		this.isSickUsedSet = isSickUsedSet;
	}

	@XmlTransient
	public boolean isAnnualUsedSet() {
		return isAnnualUsedSet;
	}

	public void setAnnualUsedSet(boolean isAnnualUsedSet) {
		this.isAnnualUsedSet = isAnnualUsedSet;
	}
	
}
