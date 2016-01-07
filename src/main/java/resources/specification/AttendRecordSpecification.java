package resources.specification;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.QueryParam;

import org.springframework.data.jpa.domain.Specification;

import com.google.common.base.Strings;

import entity.AttendRecord;
import entity.AttendRecordType;
import entity.Employee;

public class AttendRecordSpecification implements Specification<AttendRecord> {

	@QueryParam("employeeName")
	private Long employeeName;
	@QueryParam("employeeId")
	private Long employeeId;
	@QueryParam("endDate")
	private Date endDate;
	@QueryParam("bookDate")
	private Date bookDate;
	@QueryParam("startDate")
	private Date startDate;
	@QueryParam("typeName")
	private String typeName;

	@Override
	public Predicate toPredicate(Root<AttendRecord> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (!Strings.isNullOrEmpty(typeName)) {
			predicates.add(cb.like(root.<AttendRecordType> get("type")
					.<String> get("name"), "%" + typeName + "%"));
		}
		if (startDate != null) {
			predicates.add(cb.greaterThanOrEqualTo(
					root.<Date> get("startDate"), startDate));
		}
		if (endDate != null) {
			predicates.add(cb.lessThanOrEqualTo(root.<Date> get("endDate"),
					endDate));
		}
		if (bookDate != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.<Date> get("bookDate"),
					bookDate));
		}

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

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
