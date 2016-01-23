package resources.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.QueryParam;

import org.springframework.data.jpa.domain.Specification;

import com.google.common.base.Strings;

import entity.Leavesetting;

public class LeavesettingSpecification implements Specification<Leavesetting> {

	@QueryParam("type")
	private String type;
	@QueryParam("year")
	private Long year;
	@QueryParam("name")
	private String name;

	@Override
	public Predicate toPredicate(Root<Leavesetting> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (!Strings.isNullOrEmpty(type)) {
			predicates.add(cb.like(root.<Leavesetting> get("type")
					.<String> get("name"), "%" + type + "%"));
		}
		if (year != null) {
			predicates.add(cb.equal(root.<Long> get("year"), year));
		}
		if (name != null) {
			predicates
					.add(cb.like(root.<String> get("name"), "%" + name + "%"));
		}
		if (predicates.isEmpty()) {
			return null;
		}

		return cb.and(predicates.toArray(new Predicate[0]));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getYear() {
		return year;
	}

	public void setYear(Long year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
