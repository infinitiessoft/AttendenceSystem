package entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "record_type", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class AttendRecordType extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "name", unique = true, nullable = false, length = 20)
	private String name;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "type")
	private Set<AttendRecord> attendRecords = new HashSet<AttendRecord>(0);

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

	public Set<AttendRecord> getAttendRecords() {
		return attendRecords;
	}

	public void setAttendRecords(Set<AttendRecord> attendRecords) {
		this.attendRecords = attendRecords;
	}

}
