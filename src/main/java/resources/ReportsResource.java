package resources;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import resources.specification.AttendRecordSpecification;
import service.AttendRecordService;
import transfer.AttendRecordReport;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/report")
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
