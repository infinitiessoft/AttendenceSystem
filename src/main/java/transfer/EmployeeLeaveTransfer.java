package transfer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

public class EmployeeLeaveTransfer implements Serializable {

	private static final long serialVersionUID = 1L;

	public static class Employee implements Serializable {

		private static final long serialVersionUID = 1L;
		private Long id;
		private String name;

		private boolean isIdSet;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			isIdSet = true;
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

	public static class Leavesetting implements Serializable {

		public static class Type implements Serializable {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private Long id;
			private String name;

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

		}

		private static final long serialVersionUID = 1L;
		private Long id;
		private String name;
		private Double days;
		private Long year;
		private Type type;

		private boolean isIdSet;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			isIdSet = true;
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Double getDays() {
			return days;
		}

		public void setDays(Double days) {
			this.days = days;
		}

		@XmlTransient
		public boolean isIdSet() {
			return isIdSet;
		}

		@XmlTransient
		public void setIdSet(boolean isIdSet) {
			this.isIdSet = isIdSet;
		}

		public Long getYear() {
			return year;
		}

		public void setYear(Long year) {
			this.year = year;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}
	}

	private Long id;
	private Employee employee;
	private Leavesetting leavesetting;
	private Double usedDays;

	private boolean isEmployeeSet;
	private boolean isLeavesettingSet;
	private boolean isUsedDaysSet;

	public EmployeeLeaveTransfer() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.isEmployeeSet = true;
		this.employee = employee;
	}

	public Leavesetting getLeavesetting() {
		return leavesetting;
	}

	public void setLeavesetting(Leavesetting leavesetting) {
		this.isLeavesettingSet = true;
		this.leavesetting = leavesetting;
	}

	public Double getUsedDays() {
		return usedDays;
	}

	public void setUsedDays(Double usedDays) {
		this.isUsedDaysSet = true;
		this.usedDays = usedDays;
	}

	@XmlTransient
	public boolean isEmployeeSet() {
		return isEmployeeSet;
	}

	@XmlTransient
	public void setEmployeeSet(boolean isEmployeeSet) {
		this.isEmployeeSet = isEmployeeSet;
	}

	@XmlTransient
	public boolean isLeavesettingSet() {
		return isLeavesettingSet;
	}

	@XmlTransient
	public void setLeavesettingSet(boolean isLeavesettingSet) {
		this.isLeavesettingSet = isLeavesettingSet;
	}

	@XmlTransient
	public boolean isUsedDaysSet() {
		return isUsedDaysSet;
	}

	@XmlTransient
	public void setUsedDaysSet(boolean isUsedDaysSet) {
		this.isUsedDaysSet = isUsedDaysSet;
	}
}
