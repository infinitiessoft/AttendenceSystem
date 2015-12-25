package entity;

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
public class Job extends AbstractEntity {
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
	private JobType job_type;

	public Job() {
		super();
	}

	public Job(Long id, String name, Long department_id, Long job_type_id, JobType job_type) {
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

	public JobType getJob_type() {
		return job_type;
	}

	public void setJob_type(JobType job_type) {
		this.job_type = job_type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
