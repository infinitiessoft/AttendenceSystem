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

	@QueryParam("applicantName")
	private String applicantName;
	@QueryParam("applicantId")
	private Long applicantId;
	@QueryParam("endDate")
	private Date endDate;
	@QueryParam("bookDate")
	private Date bookDate;
	@QueryParam("startDate")
	private Date startDate;
	@QueryParam("typeName")
	private String typeName;
	@QueryParam("status")
	private String status;

	private Long id;

	@Override
	public Predicate toPredicate(Root<AttendRecord> root,
			CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (!Strings.isNullOrEmpty(typeName)) {
			predicates.add(cb.like(root.<AttendRecordType> get("type")
					.<String> get("name"), "%" + typeName + "%"));
		}
		if (!Strings.isNullOrEmpty(status)) {
			predicates.add(cb.like(root.<String> get("status"), "%" + status
					+ "%"));
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

		if (applicantId != null) {
			predicates.add(cb.equal(
					root.<Employee> get("employee").<Long> get("id"),
					applicantId));
		}

		if (id != null) {
			predicates.add(cb.equal(root.<Long> get("id"), id));
		}

		if (applicantName != null) {
			predicates.add(cb.equal(
					root.<Employee> get("employee").<Long> get("name"),
					applicantName));
		}

		if (predicates.isEmpty()) {
			return null;
		}

		return cb.and(predicates.toArray(new Predicate[0]));
	}

	public Long getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Long applicantId) {
		this.applicantId = applicantId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
