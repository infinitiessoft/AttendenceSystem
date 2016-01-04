package resources;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import transfer.LeavesettingTransfer;

public class LeavesettingsResourceTest extends ResourceTest{
	
	@Test
	public void testGetLeavesetting() {
		Response response = target("leavesetting").path("1").register(JacksonFeature.class).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		LeavesettingTransfer transfer = response.readEntity(LeavesettingTransfer.class);
		assertEquals(1l, transfer.getId().longValue());
		assertEquals("test", transfer.getName());
	}

	@Override
	Class<?> getResource() {
		return LeavesettingsResource.class;
	}

}
