package resources;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import resources.specification.EventSpecification;
import resources.specification.SimplePageRequest;
import service.EventService;
import transfer.EventTransfer;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/event")
public class EventResource {

	@Autowired
	private EventService eventService;

	@GET
	@Path(value = "{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventTransfer getEventt(@PathParam("id") long id) {
		return eventService.retrieve(id);
	}

	// **Method to update
	@PUT
	@Path(value = "{id}")
	public EventTransfer updateEventt(@PathParam("id") long id,
			EventTransfer department) {
		return eventService.update(id, department);
	}

	@GET
	public Page<EventTransfer> findallEventt(
			@BeanParam SimplePageRequest pageRequest,
			@BeanParam EventSpecification spec) {
		return eventService.findAll(spec, pageRequest);
	}

}
