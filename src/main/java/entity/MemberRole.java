package entity;

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
@Table(name = "member_role")
public class MemberRole extends AbstractEntity {
	private static final long serialVersionUID = 7711505597348200997L;

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long member_id;

	@Column(name = "role_id", nullable = false)
	private Long role_id;

	@ManyToOne
	@JoinColumn(name = "role")
	private Role role;

	@ManyToOne
	@JoinColumn(name = "member")
	private Member member;

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public MemberRole(Long member_id, Long role_id, Role role, Member member) {
		super();
		this.member_id = member_id;
		this.role_id = role_id;
		this.role = role;
		this.member = member;
	}

	public MemberRole() {
		super();
	}

}
