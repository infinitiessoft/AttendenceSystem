package entity;

import java.io.Serializable;

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
@Table(name = "MemberRole")

public class MemberRole implements Serializable {
	private static final long serialVersionUID = 7711505597348200997L;

	@Id
	@Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long member_id;

	@Column(name = "role_id", nullable = false)
	private Long role_id;

	@ManyToOne
	@JoinColumn(name = "role")
	private RoleEntity role;

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

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public MemberRole(Long member_id, Long role_id, RoleEntity role, Member member) {
		super();
		this.member_id = member_id;
		this.role_id = role_id;
		this.role = role;
		this.member = member;
	}

	public MemberRole() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((member == null) ? 0 : member.hashCode());
		result = prime * result + ((member_id == null) ? 0 : member_id.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((role_id == null) ? 0 : role_id.hashCode());
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
		MemberRole other = (MemberRole) obj;
		if (member == null) {
			if (other.member != null)
				return false;
		} else if (!member.equals(other.member))
			return false;
		if (member_id == null) {
			if (other.member_id != null)
				return false;
		} else if (!member_id.equals(other.member_id))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (role_id == null) {
			if (other.role_id != null)
				return false;
		} else if (!role_id.equals(other.role_id))
			return false;
		return true;
	}

}
