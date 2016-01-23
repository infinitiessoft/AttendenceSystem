package resources.version1.admin;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import resources.CSVMessageBodyWritter;
import resources.ResourceTest;

public class ReportsResourceTest extends ResourceTest {

	@Test
	public void testExportAllAttendRecord() {
		Response response = target("reports").path("records").request()
				.header("user", "demo").get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		assertEquals("attachment; filename=\"export.csv\"",
				response.getHeaderString("Content-Disposition"));
		assertEquals("text/csv", response.getHeaderString("Content-Type"));
	}

	@Override
	protected Class<?>[] getResource() {
		return new Class<?>[] { ReportsResource.class,
				CSVMessageBodyWritter.class };
	}

}
