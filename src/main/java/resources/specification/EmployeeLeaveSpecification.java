package resources.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.QueryParam;

import org.springframework.data.jpa.domain.Specification;

import entity.Employee;
import entity.EmployeeLeave;
import entity.Leavesetting;

public class EmployeeLeaveSpecification implements Specification<EmployeeLeave>{

	@QueryParam("employeeId")
	private Long employeeId;
	@QueryParam("employeeName")
	private String employeeName;
	@QueryParam("leavesettingId")
	private Long leavesettingId;
	
	@Override
	public Predicate toPredicate(Root<EmployeeLeave> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (employeeId != null) {
			predicates.add(cb.equal(
					root.<Employee> get("employee").<Long> get("id"),
					employeeId));
		}
		if (employeeName != null) {
			predicates.add(cb.equal(
					root.<Employee> get("employee").<Long> get("name"),
					employeeName));
		}
		if (leavesettingId != null) {
			predicates.add(cb.equal(
					root.<Leavesetting> get("leavesetting").<Long> get("id"),
					leavesettingId));
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

}
