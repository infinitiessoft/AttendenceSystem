package resources.version1.member;

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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import resources.specification.EventSpecification;
import resources.specification.SimplePageRequest;
import service.EmployeeService;
import service.EventService;
import transfer.EventTransfer;
import transfer.Metadata;
import exceptions.EventNotFoundException;

@Component
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PreAuthorize("isAuthenticated() and #id == principal.id or hasAuthority('admin')")
public class MemberEventsResource {

	@Autowired
	private EventService eventService;

	@Autowired
	private EmployeeService employeeService;

	@GET
	public Page<EventTransfer> findAll(@PathParam("id") long id,
			@BeanParam EventSpecification spec,
			@BeanParam SimplePageRequest pageRequest) {
		spec.setApproverId(id);
		return eventService.findAll(spec, pageRequest);
	}

	@GET
	@Path(value = "{eventid}")
	public EventTransfer getEvent(@PathParam("id") long id,
			@PathParam("eventid") long eventId) {
		EventSpecification spec = new EventSpecification();
		spec.setApproverId(id);
		spec.setId(eventId);

		Page<EventTransfer> lists = eventService.findAll(spec, null);
		EventTransfer ret = lists.getContent().size() > 0 ? lists.getContent()
				.get(0) : null;
		if (ret == null) {
			throw new EventNotFoundException(eventId);
		}
		return ret;
	}

	@PUT
	@Path(value = "{eventid}")
	public EventTransfer updateEvent(@PathParam("id") long id,
			@PathParam("eventid") long eventId, EventTransfer department) {
		getEvent(id, eventId);
		return eventService.update(eventId, department);
	}

	@GET
	@Path("metadata")
	public Metadata getMetadata(@PathParam("id") long id) {
		return eventService.retrieveMetadataByEmployeeId(id);
	}
}
