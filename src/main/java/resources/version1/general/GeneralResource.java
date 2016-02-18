package resources.version1.general;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import resources.specification.SimplePageRequest;
import service.AttendRecordService;
import transfer.AttendRecordTransfer;
import transfer.AttendRecordTransfer.Status;
import transfer.DurationTransfer;
import util.CalendarUtils;

import com.google.common.base.Preconditions;

import entity.AttendRecord;
import exceptions.InvalidEndDateException;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/general")
public class GeneralResource {

	@Autowired
	private AttendRecordService attendRecordService;

	@GET
	@Path("/records/today")
	@PreAuthorize("isAuthenticated()")
	public Page<AttendRecordTransfer> findRecordsToday(
			@BeanParam SimplePageRequest pageRequest) {
		Specification<AttendRecord> spec = new Specification<AttendRecord>() {

			@Override
			public Predicate toPredicate(Root<AttendRecord> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Calendar start = Calendar.getInstance();
				start.set(Calendar.HOUR_OF_DAY, 10);
				start.set(Calendar.MINUTE, 1);

				Calendar end = Calendar.getInstance();
				end.set(Calendar.HOUR_OF_DAY, 17);
				end.set(Calendar.MINUTE, 59);
				Date todayS = start.getTime();
				Date todayE = end.getTime();
				Expression<Date> startDate = root.<Date> get("startDate");
				Expression<Date> endDate = root.<Date> get("endDate");
				return cb.and(cb.equal(root.<String> get("status"),
						Status.permit.name()), cb.or(
						cb.between(cb.literal(todayS), startDate, endDate),
						cb.between(cb.literal(todayE), startDate, endDate)));
			}
		};
		return attendRecordService.findAll(spec, pageRequest);
	}

	@POST
	@Path("/duration")
	@PreAuthorize("isAuthenticated()")
	public DurationTransfer countDuration(DurationTransfer duration) {
		Preconditions.checkNotNull(duration.getStartDate());
		Preconditions.checkNotNull(duration.getEndDate());
		if (!duration.getEndDate().after(duration.getStartDate())) {
			throw new InvalidEndDateException();
		}
		double d = CalendarUtils.countDuration(duration.getStartDate(),
				duration.getEndDate());
		duration.setDays(d);
		return duration;
	}
}
