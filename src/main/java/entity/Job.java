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

@Entity
@Table(name = "job")

public class Job implements Serializable {
	private static final long serialVersionUID = 7711505597348200997L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "department_id", nullable = false)
	private Long department_id;

	@Column(name = "job_type_id", nullable = false)
	private Long job_type_id;

	@ManyToOne
	@JoinColumn(name = "job_type")
	private Job_type job_type;

	public Job() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Job(Long id, String name, Long department_id, Long job_type_id, Job_type job_type) {
		super();
		this.id = id;
		this.name = name;
		this.department_id = department_id;
		this.job_type_id = job_type_id;
		this.job_type = job_type;
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

	public Long getDepartment_id() {
		return department_id;
	}

	public void setDepartment_id(Long department_id) {
		this.department_id = department_id;
	}

	public Long getJob_type_id() {
		return job_type_id;
	}

	public void setJob_type_id(Long job_type_id) {
		this.job_type_id = job_type_id;
	}

	public Job_type getJob_type() {
		return job_type;
	}

	public void setJob_type(Job_type job_type) {
		this.job_type = job_type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((department_id == null) ? 0 : department_id.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((job_type == null) ? 0 : job_type.hashCode());
		result = prime * result + ((job_type_id == null) ? 0 : job_type_id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Job other = (Job) obj;
		if (department_id == null) {
			if (other.department_id != null)
				return false;
		} else if (!department_id.equals(other.department_id))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (job_type == null) {
			if (other.job_type != null)
				return false;
		} else if (!job_type.equals(other.job_type))
			return false;
		if (job_type_id == null) {
			if (other.job_type_id != null)
				return false;
		} else if (!job_type_id.equals(other.job_type_id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
