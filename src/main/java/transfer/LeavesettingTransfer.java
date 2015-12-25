package transfer;

public class LeavesettingTransfer {

	private Long id;
	private String name;
	private Long official;
	private Long personal;
	private Long sick;
	private Long special;

	public LeavesettingTransfer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeavesettingTransfer(Long id, String name, Long official, Long personal, Long sick, Long special) {
		super();
		this.id = id;
		this.name = name;
		this.official = official;
		this.personal = personal;
		this.sick = sick;
		this.special = special;
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

	public Long getOfficial() {
		return official;
	}

	public void setOfficial(Long official) {
		this.official = official;
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

	public Long getSpecial() {
		return special;
	}

	public void setSpecial(Long special) {
		this.special = special;
	}

}
