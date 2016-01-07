package resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;
import org.springframework.data.domain.Page;

import entity.AttendRecordType;
import entity.Leavesetting;
import transfer.LeavesettingTransfer;

public class LeavesettingsResourceTest extends ResourceTest {

	@Test
	public void testGetLeavesetting() {
		Response response = target("leavesetting").path("1").register(JacksonFeature.class).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		LeavesettingTransfer transfer = response.readEntity(LeavesettingTransfer.class);
		assertEquals(1L, transfer.getId().longValue());
		assertEquals("sick", transfer.getType().getName());
	}

	@Test
	public void testGetLeavesettingWithNotFoundException() {
		Response response = target("leavesetting").path("2").register(JacksonFeature.class).request().get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testDeleteLeavesetting() {
		Response response = target("leavesetting").path("1").register(JacksonFeature.class).request().delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteLeavesettingWithNotFoundException() {
		Response response = target("leavesetting").path("2").register(JacksonFeature.class).request().delete();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testSaveLeavesetting() {
		Leavesetting leavesetting = new Leavesetting();
		leavesetting.setDays(5L);
		leavesetting.setYear(2L);

		AttendRecordType type = new AttendRecordType();
		type.setId(1L);
		leavesetting.setType(type);
		Response response = target("leavesetting").register(JacksonFeature.class).request()
				.post(Entity.json(leavesetting));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		LeavesettingTransfer transfer = response.readEntity(LeavesettingTransfer.class);
		assertEquals(2L, transfer.getId().longValue());
		assertEquals(leavesetting.getType().getId(), type.getId());
	}

	@Test
	public void testUpdateLeavesetting() {
		Leavesetting leavesetting = new Leavesetting();
		leavesetting.setDays(5L);
		leavesetting.setYear(2L);

		AttendRecordType type = new AttendRecordType();
		type.setId(1L);
		leavesetting.setType(type);
		Response response = target("leavesetting").path("1").register(JacksonFeature.class).request()
				.put(Entity.json(leavesetting));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		LeavesettingTransfer transfer = response.readEntity(LeavesettingTransfer.class);
		assertEquals(1L, transfer.getId().longValue());
		assertEquals(leavesetting.getDays(), transfer.getDays());
	}

	@Test
	public void testUpdateLeavesettingWithNotFoundException() {
		Leavesetting leavesetting = new Leavesetting();
		leavesetting.setDays(5L);
		leavesetting.setYear(2L);

		AttendRecordType type = new AttendRecordType();
		type.setId(1L);
		leavesetting.setType(type);
		Response response = target("leavesetting").path("2").register(JacksonFeature.class).request()
				.put(Entity.json(leavesetting));
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testFindAllLeavesettings() {
		Response response = target("leavesetting").register(JacksonFeature.class)
				.request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		Page<LeavesettingTransfer> rets = response
				.readEntity(new GenericType<Page<LeavesettingTransfer>>() {
				});
		assertEquals(1, rets.getSize());
		LeavesettingTransfer transfer = rets.iterator().next();
		assertEquals(1L, transfer.getId().longValue());
	}

	@Override
	Class<?> getResource() {
		return LeavesettingsResource.class;
	}

}
