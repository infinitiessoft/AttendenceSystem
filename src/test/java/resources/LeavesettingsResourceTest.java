package resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import entity.PageModel;
import transfer.LeavesettingTransfer;
import assertion.AssertUtils;

public class LeavesettingsResourceTest extends ResourceTest {

	@Test
	public void testGetLeavesetting() {
		Response response = target("leavesettings").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		LeavesettingTransfer transfer = response
				.readEntity(LeavesettingTransfer.class);
		assertEquals(1L, transfer.getId().longValue());
		assertEquals("sick", transfer.getType().getName());
	}

	@Test
	public void testGetLeavesettingWithNotFoundException() {
		Response response = target("leavesettings").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testDeleteLeavesetting() {
		Response response = target("leavesettings").path("2")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteLeavesettingWithNotFoundException() {
		Response response = target("leavesettings").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testSaveLeavesetting() {
		LeavesettingTransfer leavesetting = new LeavesettingTransfer();
		leavesetting.setName("test");
		leavesetting.setDays(5d);
		leavesetting.setYear(3L);
		LeavesettingTransfer.Type type = new LeavesettingTransfer.Type();
		type.setId(2L);
		leavesetting.setType(type);

		Response response = target("leavesettings")
				.register(JacksonFeature.class).request()
				.header("user", "demo").post(Entity.json(leavesetting));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		LeavesettingTransfer transfer = response
				.readEntity(LeavesettingTransfer.class);
		assertEquals(3L, transfer.getId().longValue());
		assertEquals(leavesetting.getType().getId(), transfer.getType().getId());
		assertEquals(leavesetting.getName(), transfer.getName());
		assertEquals(leavesetting.getYear(), transfer.getYear());
		assertEquals(leavesetting.getDays(), transfer.getDays());
	}

	@Test
	public void testSaveLeavesettingWithDuplicateYearType() {
		LeavesettingTransfer leavesetting = new LeavesettingTransfer();
		leavesetting.setDays(5d);
		leavesetting.setYear(2L);
		LeavesettingTransfer.Type type = new LeavesettingTransfer.Type();
		type.setId(1L);
		leavesetting.setType(type);

		Response response = target("leavesettings")
				.register(JacksonFeature.class).request()
				.header("user", "demo").post(Entity.json(leavesetting));
		AssertUtils.assertBadRequest(response);
	}

	@Test
	public void testUpdateLeavesetting() {
		LeavesettingTransfer leavesetting = new LeavesettingTransfer();
		leavesetting.setName("test");
		leavesetting.setDays(5d);
		leavesetting.setYear(3L);
		LeavesettingTransfer.Type type = new LeavesettingTransfer.Type();
		type.setId(2L);
		leavesetting.setType(type);
		Response response = target("leavesettings").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(leavesetting));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		LeavesettingTransfer transfer = response
				.readEntity(LeavesettingTransfer.class);
		assertEquals(1L, transfer.getId().longValue());
		assertEquals(leavesetting.getType().getId(), transfer.getType().getId());
		assertEquals(leavesetting.getName(), transfer.getName());
		assertEquals(leavesetting.getYear(), transfer.getYear());
		assertEquals(leavesetting.getDays(), transfer.getDays());
	}

	@Test
	public void testUpdateLeavesettingWithNotFoundException() {
		LeavesettingTransfer leavesetting = new LeavesettingTransfer();
		leavesetting.setDays(5d);
		leavesetting.setYear(3L);
		LeavesettingTransfer.Type type = new LeavesettingTransfer.Type();
		type.setId(1L);
		leavesetting.setType(type);

		Response response = target("leavesettings").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(leavesetting));
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testUpdateLeavesettingWithDuplicateYearType() {
		LeavesettingTransfer leavesetting = new LeavesettingTransfer();
		leavesetting.setDays(5d);
		leavesetting.setYear(2L);
		LeavesettingTransfer.Type type = new LeavesettingTransfer.Type();
		type.setId(1L);
		leavesetting.setType(type);

		Response response = target("leavesettings").path("1")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(leavesetting));
		AssertUtils.assertBadRequest(response);
	}

	@Test
	public void testFindAllLeavesettings() {
		Response response = target("leavesettings")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		PageModel<LeavesettingTransfer> rets = response
				.readEntity(new GenericType<PageModel<LeavesettingTransfer>>() {
				});
		assertEquals(2, rets.getTotalElements());
	}

	@Override
	Class<?>[] getResource() {
		return new Class<?>[] { LeavesettingsResource.class };
	}

}
