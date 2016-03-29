package resources.version1.admin;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import resources.ResourceTest;
import transfer.LeavesettingTransfer;
import assertion.AssertUtils;
import entity.PageModel;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
	public void test1GetLeavesettingWithNotFoundException() {
		Response response = target("leavesettings").path("4")
				.register(JacksonFeature.class).request()
				.header("user", "demo").get();
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void test1DeleteLeavesettingWithRemovingIntegrityViolationException() {
		Response response = target("leavesettings").path("2")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		AssertUtils.assertConflict(response);
	}

	@Test
	public void testZDeleteLeavesetting() {
		Response response = target("leavesettings").path("3")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());
	}

	@Test
	public void test1DeleteLeavesettingWithNotFoundException() {
		Response response = target("leavesettings").path("4")
				.register(JacksonFeature.class).request()
				.header("user", "demo").delete();
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void testZSaveLeavesetting() {
		LeavesettingTransfer leavesetting = new LeavesettingTransfer();
		leavesetting.setName("test2");
		leavesetting.setDays(5d);
		leavesetting.setYear(2L);
		LeavesettingTransfer.Type type = new LeavesettingTransfer.Type();
		type.setId(2L);
		leavesetting.setType(type);

		Response response = target("leavesettings")
				.register(JacksonFeature.class).request()
				.header("user", "demo").post(Entity.json(leavesetting));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		LeavesettingTransfer transfer = response
				.readEntity(LeavesettingTransfer.class);
		assertEquals(4L, transfer.getId().longValue());
		assertEquals(leavesetting.getType().getId(), transfer.getType().getId());
		assertEquals(leavesetting.getName(), transfer.getName());
		assertEquals(leavesetting.getYear(), transfer.getYear());
		assertEquals(leavesetting.getDays(), transfer.getDays());
	}

	@Test
	public void test1SaveLeavesettingWithDuplicateYearType() {
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
		leavesetting.setYear(6L);
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
	public void test1UpdateLeavesettingWithNotFoundException() {
		LeavesettingTransfer leavesetting = new LeavesettingTransfer();
		leavesetting.setDays(5d);
		leavesetting.setYear(3L);
		LeavesettingTransfer.Type type = new LeavesettingTransfer.Type();
		type.setId(1L);
		leavesetting.setType(type);

		Response response = target("leavesettings").path("4")
				.register(JacksonFeature.class).request()
				.header("user", "demo").put(Entity.json(leavesetting));
		AssertUtils.assertNotFound(response);
	}

	@Test
	public void test1UpdateLeavesettingWithDuplicateYearType() {
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
		assertEquals(3, rets.getTotalElements());
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { LeavesettingsResource.class };
	}

}
