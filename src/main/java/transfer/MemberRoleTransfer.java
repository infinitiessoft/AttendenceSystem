package transfer;

public class MemberRoleTransfer {

	private Long member_id;
	private Long role_id;

	// private List<Role> role;
	// private List<Member> member;
	public MemberRoleTransfer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberRoleTransfer(Long member_id, Long role_id) {
		super();
		this.member_id = member_id;
		this.role_id = role_id;
	}

	public Long getMember_id() {
		return member_id;
	}

	public void setMember_id(Long member_id) {
		this.member_id = member_id;
	}

	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

}
