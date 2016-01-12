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
import service.EmployeeService;
import service.EventService;
import transfer.EventTransfer;
import exceptions.EventNotFoundException;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeEventsResource {

	@Autowired
	private EventService eventService;

	@Autowired
	private EmployeeService employeeService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Page<EventTransfer> findAll(@PathParam("id") long id,
			@BeanParam SimplePageRequest pageRequest) {
		EventSpecification spec = new EventSpecification();
		spec.setApproverId(id);
		return eventService.findAll(spec, pageRequest);
	}

	@GET
	@Path(value = "{eventid}")
	@Produces(MediaType.APPLICATION_JSON)
	public EventTransfer getEvent(@PathParam("id") long employeeId,
			@PathParam("eventid") long id) {
		EventSpecification spec = new EventSpecification();
		spec.setApproverId(employeeId);
		spec.setId(id);

		Page<EventTransfer> lists = eventService.findAll(spec, null);
		EventTransfer ret = lists.getContent().size() > 0 ? lists.getContent()
				.get(0) : null;
		if (ret == null) {
			throw new EventNotFoundException(id);
		}
		return ret;
	}

	@PUT
	@Path(value = "{eventid}")
	public EventTransfer updateEvent(@PathParam("id") long employeeId,
			@PathParam("eventid") long id, EventTransfer department) {
		getEvent(employeeId, id);
		return eventService.update(id, department);
	}
}
