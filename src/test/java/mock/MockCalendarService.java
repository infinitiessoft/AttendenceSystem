package mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import resources.specification.CalendarEventSpecification;
import service.CalendarEventService;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

public class MockCalendarService implements CalendarEventService {

	private static final Logger logger = LoggerFactory
			.getLogger(MockCalendarService.class);
	private static final List<Event> events = new ArrayList<Event>();
	static {
		Event event = new Event();
		EventDateTime start = new EventDateTime();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(2015, 0, 4, 0, 0, 0);
		DateTime startDateTime = new DateTime(startCalendar.getTime());
		start.setDate(startDateTime);
		

		EventDateTime end = new EventDateTime();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(2015, 0, 6, 23, 59, 0);
		DateTime endDateTime = new DateTime(endCalendar.getTime());
		end.setDate(endDateTime);
		
		Event event1 = new Event();
		event1.setSummary("\u6625\u5047");
		event1.setStart(start);
		event1.setEnd(end);
		events.add(event1);
		
		event.setSummary("\u88dc\u73ed");
		startCalendar.set(2015, 0, 6, 0, 0, 0);
		EventDateTime start2 = new EventDateTime();
		DateTime startDateTime2 = new DateTime(startCalendar.getTime());
		start2.setDate(startDateTime2);
		event.setStart(start2);
		event.setEnd(end);
		events.add(event);

		
	}

	@Override
	public List<Event> findAll(CalendarEventSpecification spec,
			Pageable pageable) {

		return events;
	}

	@Override
	public Event save(Event event) {
		logger.debug("save new event");
		return null;
	}

}
