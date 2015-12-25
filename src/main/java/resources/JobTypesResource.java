package resources;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import service.JobTypeService;
import transfer.JobTypeTransfer;
import entity.JobType;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/jobtype")
public class JobTypesResource {

	@Autowired
	private JobTypeService jobTypeService;

	@GET
	@Path(value = "{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public JobTypeTransfer getJob(@PathParam("id") long id) {
		return jobTypeService.retrieve(id);
	}

	// ** Method to delete
	@DELETE
	@Path(value = "{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteJob(@PathParam("id") long id) {
		jobTypeService.delete(id);
		return Response.status(Status.OK)
				.entity("job has been successfully deleted")
				.type(MediaType.APPLICATION_JSON).build();
	}

	// **Method to update
	@PUT
	@Path(value = "{id}")
	public JobTypeTransfer updateJob(@PathParam("id") long id, JobType jobType) {
		return jobTypeService.update(id, jobType);
	}

	// **Method to save or create
	@POST
	public JobTypeTransfer saveJob(JobType jobType) {
		return jobTypeService.save(jobType);
	}

	// ** Method to find All the jobs in the list

	@GET
	public Collection<JobTypeTransfer> findallJob() {
		return jobTypeService.findAll();
	}

}
