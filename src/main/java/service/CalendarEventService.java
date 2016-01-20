package service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import resources.specification.CalendarEventSpecification;

import com.google.api.services.calendar.model.Event;

public interface CalendarEventService {

	public abstract List<Event> findAll(CalendarEventSpecification spec,
			Pageable pageable);

	public abstract Event save(Event event);

}