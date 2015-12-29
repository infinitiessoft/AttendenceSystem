package resources;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.Test;

import entity.JobType;
import transfer.JobTypeTransfer;

public class JobTypesResourceTest extends ResourceTest {

	@Test
	public void testGetJobType() {
		Response response = target("jobtype").path("1").register(JacksonFeature.class).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		JobTypeTransfer transfer = response.readEntity(JobTypeTransfer.class);
		assertEquals("1", transfer.getId());
		assertEquals("pohsun", transfer.getName());
	}

	@Test
	public void testGetJobTypeWithNotFoundException() {
		Response response = target("jobtype").path("2").register(JacksonFeature.class).request().get();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testDeleteJobType() {
		Response response = target("jobtype").path("1").register(JacksonFeature.class).request().delete();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	public void testDeleteJobTypeWithNotFoundException() {
		Response response = target("jobtype").path("2").register(JacksonFeature.class).request().delete();
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testUpdateJobType() {
		JobType admin = new JobType();
		admin.setName("administrator");
		Response response = target("jobtype").path("1").register(JacksonFeature.class).request()
				.put(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		JobTypeTransfer transfer = response.readEntity(JobTypeTransfer.class);
		assertEquals("1", transfer.getId());
		assertEquals(admin.getName(), transfer.getName());
	}

	@Test
	public void testUpdateJobTypeWithNotFoundException() {
		JobType admin = new JobType();
		admin.setName("administrator");
		Response response = target("jobtype").path("2").register(JacksonFeature.class).request()
				.put(Entity.json(admin));
		assertEquals(Status.NOT_FOUND.getStatusCode(), response.getStatus());
		assertEquals("true", response.getHeaderString("safe"));
	}

	@Test
	public void testSaveJobType() {
		JobType admin = new JobType();
		admin.setName("administrator");
		Response response = target("jobtype").register(JacksonFeature.class).request().post(Entity.json(admin));
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		JobTypeTransfer transfer = response.readEntity(JobTypeTransfer.class);
		assertEquals("2", transfer.getId());
		assertEquals(admin.getName(), transfer.getName());
	}

	@Test
	public void testSaveJobTypeWithDuplicateName() {
		JobType admin = new JobType();
		admin.setName("administrator");
		Response response = target("jobtype").register(JacksonFeature.class).request().post(Entity.json(admin));
		assertEquals(Status.BAD_REQUEST.getStatusCode(), response.getStatus());
	}

	@Test
	public void testFindallJobType() {
		Response response = target("jobtype").register(JacksonFeature.class).request().get();
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
		Collection<JobTypeTransfer> rets = response.readEntity(new GenericType<Collection<JobTypeTransfer>>() {
		});
		assertEquals(1, rets.size());
		JobTypeTransfer transfer = rets.iterator().next();
		assertEquals("1", transfer.getId());
		assertEquals("pohsun", transfer.getName());

	}

	@Override
	Class<?> getResource() {
		return JobTypesResource.class;
	}

}
