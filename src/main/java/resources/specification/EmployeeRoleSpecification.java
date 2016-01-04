package resources.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import entity.Employee;
import entity.EmployeeRole;
import entity.Role;

public class EmployeeRoleSpecification implements Specification<EmployeeRole> {

	private Long employeeId;
	private Long roleId;

	@Override
	public Predicate toPredicate(Root<EmployeeRole> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (employeeId != null) {
			predicates.add(cb.equal(
					root.<Employee> get("employee").<Long> get("id"),
					employeeId));
		}
		if (roleId != null) {
			predicates.add(cb.equal(root.<Role> get("role").<Long> get("id"),
					roleId));
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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
