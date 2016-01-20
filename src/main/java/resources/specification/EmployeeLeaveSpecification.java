package resources.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.QueryParam;

import org.springframework.data.jpa.domain.Specification;

import com.google.api.client.util.Strings;

import entity.AttendRecordType;
import entity.Employee;
import entity.EmployeeLeave;
import entity.Leavesetting;

public class EmployeeLeaveSpecification implements Specification<EmployeeLeave> {

	@QueryParam("employeeId")
	private Long employeeId;
	@QueryParam("employeeName")
	private String employeeName;
	@QueryParam("leavesettingId")
	private Long leavesettingId;
	@QueryParam("typeName")
	private String typeName;
	@QueryParam("year")
	private Long year;

	@Override
	public Predicate toPredicate(Root<EmployeeLeave> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (employeeId != null) {
			predicates.add(cb.equal(
					root.<Employee> get("employee").<Long> get("id"),
					employeeId));
		}
		if (employeeName != null) {
			predicates.add(cb.like(root.<Employee> get("employee")
					.<String> get("name"), "%" + employeeName + "%"));
		}
		if (leavesettingId != null) {
			predicates.add(cb.equal(root.<Leavesetting> get("leavesetting")
					.<Long> get("id"), leavesettingId));
		}
		if (!Strings.isNullOrEmpty(typeName)) {
			predicates.add(cb.like(root.<Leavesetting> get("leavesetting")
					.<AttendRecordType> get("type").<String> get("name"), "%"
					+ typeName + "%"));
		}
		if (year != null) {
			predicates.add(cb.equal(root.<Leavesetting> get("leavesetting")
					.<Long> get("year"), year));
		}
		if (predicates.isEmpty()) {
			return null;
		}

		return cb.and(predicates.toArray(new Predicate[0]));
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getLeavesettingId() {
		return leavesettingId;
	}

	public void setLeavesettingId(Long leavesettingId) {
		this.leavesettingId = leavesettingId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

}
