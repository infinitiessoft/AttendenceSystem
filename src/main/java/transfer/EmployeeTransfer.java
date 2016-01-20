package transfer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

public class EmployeeTransfer {

	public static class Role {

		private Long id;
		private String name;

		private boolean isIdSet;

		public Role() {
			super();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			setIdSet(true);
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@XmlTransient
		public boolean isIdSet() {
			return isIdSet;
		}

		@XmlTransient
		public void setIdSet(boolean isIdSet) {
			this.isIdSet = isIdSet;
		}

	}

	public static class Department {

		private Long id;
		private String name;

		private boolean isIdSet;

		public Department() {
			super();
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			setIdSet(true);
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@XmlTransient
		public boolean isIdSet() {
			return isIdSet;
		}

		@XmlTransient
		public void setIdSet(boolean isIdSet) {
			this.isIdSet = isIdSet;
		}

	}

	private Long id;
	private String name;
	private String username;
	private Date dateOfJoined;
	private String email;
	private String gender;
	private Department department;
	private String password;
	private List<Role> roles = new ArrayList<Role>(0);

	private boolean isNameSet;
	private boolean isUsernameSet;
	private boolean isDateOfJoinedSet;
	private boolean isEmailSet;
	private boolean isGenderSet;
	private boolean isDepartmentSet;
	private boolean isPasswordSet;

	public EmployeeTransfer() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		isNameSet = true;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		isUsernameSet = true;
		this.username = username;
	}

	public Date getDateOfJoined() {
		return dateOfJoined;
	}

	public void setDateOfJoined(Date dateOfJoined) {
		isDateOfJoinedSet = true;
		this.dateOfJoined = dateOfJoined;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		isEmailSet = true;
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		isGenderSet = true;
		this.gender = gender;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		isDepartmentSet = true;
		this.department = department;
	}

	@XmlTransient
	public boolean isNameSet() {
		return isNameSet;
	}

	@XmlTransient
	public void setNameSet(boolean isNameSet) {
		this.isNameSet = isNameSet;
	}

	@XmlTransient
	public boolean isUsernameSet() {
		return isUsernameSet;
	}

	@XmlTransient
	public void setUsernameSet(boolean isUsernameSet) {
		this.isUsernameSet = isUsernameSet;
	}

	@XmlTransient
	public boolean isDateOfJoinedSet() {
		return isDateOfJoinedSet;
	}

	@XmlTransient
	public void setDateOfJoinedSet(boolean isDateOfJoinedSet) {
		this.isDateOfJoinedSet = isDateOfJoinedSet;
	}

	@XmlTransient
	public boolean isEmailSet() {
		return isEmailSet;
	}

	@XmlTransient
	public void setEmailSet(boolean isEmailSet) {
		this.isEmailSet = isEmailSet;
	}

	@XmlTransient
	public boolean isGenderSet() {
		return isGenderSet;
	}

	@XmlTransient
	public void setGenderSet(boolean isGenderSet) {
		this.isGenderSet = isGenderSet;
	}

	@XmlTransient
	public boolean isDepartmentSet() {
		return isDepartmentSet;
	}

	@XmlTransient
	public void setDepartmentSet(boolean isDepartmentSet) {
		this.isDepartmentSet = isDepartmentSet;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		isPasswordSet = true;
		this.password = password;
	}

	@XmlTransient
	public boolean isPasswordSet() {
		return isPasswordSet;
	}

	@XmlTransient
	public void setPasswordSet(boolean isPasswordSet) {
		this.isPasswordSet = isPasswordSet;
	}

	@XmlTransient
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}
