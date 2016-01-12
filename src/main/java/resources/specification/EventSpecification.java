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
import entity.Event;

public class EventSpecification implements Specification<Event> {

	@QueryParam("applicantName")
	private String applicantName;
	@QueryParam("applicantId")
	private Long applicantId;
	@QueryParam("approverName")
	private String approverName;
	@QueryParam("approverId")
	private Long approverId;
	@QueryParam("bookDate")
	private Date bookDate;
	@QueryParam("recordTypeName")
	private String recordTypeName;
	@QueryParam("action")
	private String action;

	@Override
	public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (!Strings.isNullOrEmpty(recordTypeName)) {
			predicates.add(
					cb.like(root.<AttendRecord> get("attendRecord").<AttendRecordType> get("type").<String> get("name"),
							"%" + recordTypeName + "%"));
		}
		if (!Strings.isNullOrEmpty(action)) {
			predicates.add(cb.like(root.<String> get("action"), "%" + action + "%"));
		}
		if (bookDate != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.<Date> get("bookDate"), bookDate));
		}

		if (approverId != null) {
			predicates.add(cb.equal(root.<Employee> get("employee").<Long> get("id"), approverId));
		}

		if (id != null) {
			predicates.add(cb.equal(root.<Long> get("id"), id));
		}

		if (approverName != null) {
			predicates.add(cb.equal(root.<Employee> get("employee").<Long> get("name"), approverName));
		}

		if (applicantId != null) {
			predicates.add(cb.equal(root.<AttendRecord> get("attendRecord").<Employee> get("employee").<Long> get("id"),
					applicantId));
		}
		if (applicantName != null) {
			predicates
					.add(cb.equal(root.<AttendRecord> get("attendRecord").<Employee> get("employee").<Long> get("name"),
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

	public Date getBookDate() {
		return bookDate;
	}

	public void setBookDate(Date bookDate) {
		this.bookDate = bookDate;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public Long getApproverId() {
		return approverId;
	}

	public void setApproverId(Long approverId) {
		this.approverId = approverId;
	}

	public String getRecordTypeName() {
		return recordTypeName;
	}

	public void setRecordTypeName(String recordTypeName) {
		this.recordTypeName = recordTypeName;
	}

	// public Long getId() {
	// return id;
	// }

	// public void setId(Long id) {
	// this.id = id;
	// }

}
