package resources;

import javax.ws.rs.BeanParam;
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
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import resources.specification.LeavesettingSpecification;
import resources.specification.SimplePageRequest;
import service.LeavesettingService;
import transfer.LeavesettingTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/leavesetting")
public class LeavesettingsResource {

	@Autowired
	private LeavesettingService leavesettingService;

	@GET
	@Path(value = "{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public LeavesettingTransfer getLeavesetting(@PathParam("id") long id) {
		return leavesettingService.retrieve(id);
	}

	@DELETE
	@Path(value = "{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteLeavesetting(@PathParam("id") long id) {
		leavesettingService.delete(id);
		return Response.status(Status.OK)
				.entity("leavesetting has been successfully deleted")
				.type(MediaType.APPLICATION_JSON).build();
	}

	@PUT
	@Path(value = "{id}")
	public LeavesettingTransfer updateLeavesetting(@PathParam("id") long id,
			LeavesettingTransfer leavesetting) {
		return leavesettingService.update(id, leavesetting);
	}

	@POST
	public LeavesettingTransfer saveLeavesetting(LeavesettingTransfer transfer) {
		return leavesettingService.save(transfer);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<LeavesettingTransfer> findAllLeavesetting(
			@BeanParam SimplePageRequest pageRequest,
			@BeanParam LeavesettingSpecification spec) {
		return leavesettingService.findAll(spec, pageRequest);
	}

}
