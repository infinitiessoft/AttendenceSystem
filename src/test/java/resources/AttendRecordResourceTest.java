package resources;

import static org.junit.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import transfer.AttendRecordTransfer;

public class AttendRecordResourceTest extends ResourceTest{

	@Test
	public void testSaveAttendRecord() throws ParseException {
		AttendRecordTransfer record = new AttendRecordTransfer();
		record.setStartDate(DateFormat.getDateTimeInstance().parse("2016/05/05 上午 10:00:00"));
		record.setEndDate(DateFormat.getDateTimeInstance().parse("2016/05/06 下午 06:00:00"));
		
		AttendRecordTransfer.Employee employee = new AttendRecordTransfer.Employee();
		employee.setId(1l);
		record.setApplicant(employee);
		
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(2l);
		record.setType(type);
		record.setReason("test");
		
		Authentication auth = new UsernamePasswordAuthenticationToken("pohsun", "pohsun");
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(auth);

		Response response = target("record")
				.register(JacksonFeature.class).request()
				.post(Entity.json(record));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		
	}
	
	@Override
	Class<?> getResource() {
		return AttendRecordsResource.class;
	}

}
