package transfer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;


public class LeavesettingTransfer implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static class Type implements Serializable {
		
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

	private Long id;
	private Type type;
	private Long year;
	private Long days;

	private boolean isTypeSet;
	private boolean isYearSet;
	private boolean isDaysSet;

	public LeavesettingTransfer() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.isTypeSet = true;
		this.type = type;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.isYearSet = true;
		this.year = year;
	}

	public Long getDays() {
		return days;
	}

	public void setDays(Long days) {
		this.isDaysSet = true;
		this.days = days;
	}

	@XmlTransient
	public boolean isTypeSet() {
		return isTypeSet;
	}
	
	@XmlTransient
	public void setTypeSet(boolean isTypeSet) {
		this.isTypeSet = isTypeSet;
	}

	@XmlTransient
	public boolean isYearSet() {
		return isYearSet;
	}

	@XmlTransient
	public void setYearSet(boolean isYearSet) {
		this.isYearSet = isYearSet;
	}

	@XmlTransient
	public boolean isDaysSet() {
		return isDaysSet;
	}

	@XmlTransient
	public void setDaysSet(boolean isDaysSet) {
		this.isDaysSet = isDaysSet;
	}

}
