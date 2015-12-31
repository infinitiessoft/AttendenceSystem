package resources.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.google.common.base.Strings;

import entity.Department;

public class DepartmentSpecification implements Specification<Department> {

	private String name;

	public DepartmentSpecification(String name) {
		this.name = name;
	}

	@Override
	public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query,
			CriteriaBuilder cb) {
		if (!Strings.isNullOrEmpty(name)) {
			return cb.like(root.<String> get("name"), "%" + name + "%");
		}
		return null;
	}
}
