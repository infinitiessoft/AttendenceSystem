package resources;

import static org.junit.Assert.fail;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringLifecycleListener;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.filter.RequestContextFilter;

public class EmployeesResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		ResourceConfig rc = new ResourceConfig(EmployeesResource.class);
		rc.register(GenericExceptionMapper.class);
		rc.register(SpringLifecycleListener.class);
		rc.register(RequestContextFilter.class);
		rc.register(JacksonFeature.class);

		rc.property("contextConfigLocation",
				"file:src/test/resource/test_context.xml");
		return rc;
	}

	@Test
	public void testGetEmployee() {
		// Response response = target("employee").path("1")
		// .register(JacksonFeature.class).request().get();
		// // .header("X-Auth-Token", TOKEN).get();
		// Assert.assertEquals(Status.OK.getStatusCode(), response.getStatus());
		Response response = target("employee").path("2")
				.register(JacksonFeature.class).request().get();
		System.err.println(response.readEntity(String.class));
		Assert.assertEquals(Status.NOT_FOUND.getStatusCode(),
				response.getStatus());
	}

	@Test
	public void testDeleteEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveEmployee() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindallEmployee() {
		fail("Not yet implemented");
	}

}
