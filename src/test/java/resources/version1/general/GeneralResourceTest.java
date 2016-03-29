package resources.version1.general;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import resources.ObjectMapperContextResolver;
import resources.ResourceTest;
import resources.version1.admin.AttendRecordsResource;
import transfer.AttendRecordTransfer;
import entity.PageModel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GeneralResourceTest extends ResourceTest {

	@Test
	public void testFindRecordsTodayWithMorningLeave() {
		Calendar startDateC = Calendar.getInstance();
		startDateC.set(Calendar.HOUR_OF_DAY, 10);
		startDateC.set(Calendar.MINUTE, 0);
		Calendar endDateC = Calendar.getInstance();
		endDateC.set(Calendar.HOUR_OF_DAY, 14);
		endDateC.set(Calendar.MINUTE, 0);
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("records").register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").post(Entity.json(record));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		response = target("general").path("records").path("today")
				.register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<AttendRecordTransfer> rets = response
				.readEntity(new GenericType<PageModel<AttendRecordTransfer>>() {
				});
		assertEquals(3, rets.getTotalElements());
	}

	@Test
	public void testFindRecordsTodayWithAfternoonLeave() {

		Calendar startDateC = Calendar.getInstance();
		startDateC.set(Calendar.HOUR_OF_DAY, 15);
		startDateC.set(Calendar.MINUTE, 0);
		Calendar endDateC = Calendar.getInstance();
		endDateC.set(Calendar.HOUR_OF_DAY, 18);
		endDateC.set(Calendar.MINUTE, 0);
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("records").register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").post(Entity.json(record));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		response = target("general").path("records").path("today")
				.register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<AttendRecordTransfer> rets = response
				.readEntity(new GenericType<PageModel<AttendRecordTransfer>>() {
				});
		assertEquals(2, rets.getTotalElements());
	}

	@Test
	public void testFindRecordsTodayCorssDays() {
		Calendar startDateC = Calendar.getInstance();
		Calendar endDateC = Calendar.getInstance();
		AttendRecordTransfer record = new AttendRecordTransfer();
		AttendRecordTransfer.Type type = new AttendRecordTransfer.Type();

		startDateC.add(Calendar.DAY_OF_YEAR, -1);
		startDateC.set(Calendar.HOUR_OF_DAY, 10);
		startDateC.set(Calendar.MINUTE, 0);
		endDateC.add(Calendar.DAY_OF_YEAR, 1);
		endDateC.set(Calendar.HOUR_OF_DAY, 18);
		endDateC.set(Calendar.MINUTE, 0);
		record = new AttendRecordTransfer();
		type = new AttendRecordTransfer.Type();
		type.setId(1L);
		record.setType(type);
		record.setStartDate(startDateC.getTime());
		record.setEndDate(endDateC.getTime());
		record.setReason("reason");

		Response response = target("records").register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").post(Entity.json(record));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

		response = target("general").path("records").path("today")
				.register(JacksonFeature.class)
				.register(ObjectMapperContextResolver.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<AttendRecordTransfer> rets = response
				.readEntity(new GenericType<PageModel<AttendRecordTransfer>>() {
				});
		assertEquals(1, rets.getTotalElements());
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { AttendRecordsResource.class,
				GeneralResource.class };
	}

}
