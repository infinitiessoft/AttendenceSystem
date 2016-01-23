package resources.version1.admin;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import resources.specification.AttendRecordSpecification;
import service.AttendRecordService;
import transfer.AttendRecordReport;

@Component
@Path("/reports")
public class ReportsResource {

	@Autowired
	private AttendRecordService attendRecordService;

	@GET
	@Path("records")
	@Produces("text/csv")
	public List<AttendRecordReport> exportAllAttendRecord(
			@BeanParam AttendRecordSpecification spec) {
		return attendRecordService.findAll(spec);
	}

}
