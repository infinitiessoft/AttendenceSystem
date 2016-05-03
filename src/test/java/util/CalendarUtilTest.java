package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.joda.time.Interval;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import exceptions.InvalidStartAndEndDateException;

public class CalendarUtilTest {

	private List<Event> events;

	@Before
	public void setUp() throws Exception {
		events = new ArrayList<Event>();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCheckNotOverlaps() {
		Event event = new Event();
		EventDateTime start = new EventDateTime();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.AM_PM, Calendar.AM);
		startCalendar.set(Calendar.HOUR_OF_DAY, 1);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		DateTime startDateTime = new DateTime(startCalendar.getTime());
		start.setDate(startDateTime);
		event.setStart(start);

		EventDateTime end = new EventDateTime();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.AM_PM, Calendar.AM);
		endCalendar.set(Calendar.HOUR_OF_DAY, 8);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		DateTime endDateTime = new DateTime(endCalendar.getTime());
		end.setDate(endDateTime);
		event.setEnd(end);
		event.setSummary("\u88dc\u73ed");
		events.add(event);

		Event event1 = new Event();
		event1.setSummary("\u6625\u5047");
		event1.setStart(start);
		event1.setEnd(end);
		events.add(event1);
		Interval interval = new Interval(startCalendar.getTimeInMillis(),
				endCalendar.getTimeInMillis());
		CalendarUtils.checkNotOverlaps(interval, events);
	}

	@Test
	public void testCheckNotOverlapsWithLargeInterval() {
		Event event = new Event();
		EventDateTime start = new EventDateTime();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.AM_PM, Calendar.AM);
		startCalendar.set(Calendar.HOUR_OF_DAY, 2);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		DateTime startDateTime = new DateTime(startCalendar.getTime());
		start.setDate(startDateTime);
		event.setStart(start);

		EventDateTime end = new EventDateTime();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.AM_PM, Calendar.AM);
		endCalendar.set(Calendar.HOUR_OF_DAY, 8);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		DateTime endDateTime = new DateTime(endCalendar.getTime());
		end.setDate(endDateTime);
		event.setEnd(end);
		event.setSummary("\u88dc\u73ed");
		events.add(event);

		Event event1 = new Event();
		event1.setSummary("\u6625\u5047");
		event1.setStart(start);
		event1.setEnd(end);
		events.add(event1);
		startCalendar.set(Calendar.HOUR_OF_DAY, 1);
		endCalendar.set(Calendar.HOUR_OF_DAY, 9);
		Interval interval = new Interval(startCalendar.getTimeInMillis(),
				endCalendar.getTimeInMillis());
		CalendarUtils.checkNotOverlaps(interval, events);
	}

	@Test
	public void testCheckNotOverlapsWithSmallInterval() {
		Event event = new Event();
		EventDateTime start = new EventDateTime();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.AM_PM, Calendar.AM);
		startCalendar.set(Calendar.HOUR_OF_DAY, 2);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		DateTime startDateTime = new DateTime(startCalendar.getTime());
		start.setDate(startDateTime);
		event.setStart(start);

		EventDateTime end = new EventDateTime();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.AM_PM, Calendar.AM);
		endCalendar.set(Calendar.HOUR_OF_DAY, 8);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		DateTime endDateTime = new DateTime(endCalendar.getTime());
		end.setDate(endDateTime);
		event.setEnd(end);
		event.setSummary("\u88dc\u73ed");
		events.add(event);

		Event event1 = new Event();
		event1.setSummary("\u6625\u5047");
		event1.setStart(start);
		event1.setEnd(end);
		events.add(event1);
		startCalendar.set(Calendar.HOUR_OF_DAY, 3);
		endCalendar.set(Calendar.HOUR_OF_DAY, 7);
		Interval interval = new Interval(startCalendar.getTimeInMillis(),
				endCalendar.getTimeInMillis());
		CalendarUtils.checkNotOverlaps(interval, events);
	}

	@Test
	public void testCheckNotOverlapsReverseSequence() {
		Event event = new Event();
		EventDateTime start = new EventDateTime();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.AM_PM, Calendar.AM);
		startCalendar.set(Calendar.HOUR_OF_DAY, 1);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		DateTime startDateTime = new DateTime(startCalendar.getTime());
		start.setDate(startDateTime);
		event.setStart(start);

		EventDateTime end = new EventDateTime();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.AM_PM, Calendar.AM);
		endCalendar.set(Calendar.HOUR_OF_DAY, 8);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		DateTime endDateTime = new DateTime(endCalendar.getTime());
		end.setDate(endDateTime);
		event.setEnd(end);
		event.setSummary("\u88dc\u73ed");

		Event event1 = new Event();
		event1.setSummary("\u6625\u5047");
		event1.setStart(start);
		event1.setEnd(end);
		events.add(event1);
		events.add(event);
		Interval interval = new Interval(startCalendar.getTimeInMillis(),
				endCalendar.getTimeInMillis());
		CalendarUtils.checkNotOverlaps(interval, events);
	}

	@Test(expected = InvalidStartAndEndDateException.class)
	public void testOverlaps() {
		Event event = new Event();
		EventDateTime start = new EventDateTime();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.AM_PM, Calendar.AM);
		startCalendar.set(Calendar.HOUR_OF_DAY, 1);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		DateTime startDateTime = new DateTime(startCalendar.getTime());
		start.setDate(startDateTime);
		event.setStart(start);

		EventDateTime end = new EventDateTime();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.AM_PM, Calendar.AM);
		endCalendar.set(Calendar.HOUR_OF_DAY, 8);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		DateTime endDateTime = new DateTime(endCalendar.getTime());
		end.setDate(endDateTime);
		event.setEnd(end);
		event.setSummary("\u88dc\u73ed");
		events.add(event);

		Event event1 = new Event();
		startCalendar.add(Calendar.DATE, 1);
		endCalendar.add(Calendar.DATE, 1);
		startDateTime = new DateTime(startCalendar.getTime());
		start = new EventDateTime();
		start.setDate(startDateTime);
		endDateTime = new DateTime(endCalendar.getTime());
		end = new EventDateTime();
		end.setDate(endDateTime);
		event1.setSummary("\u6625\u5047");
		event1.setStart(start);
		event1.setEnd(end);
		events.add(event1);
		Interval interval = new Interval(startCalendar.getTimeInMillis(),
				endCalendar.getTimeInMillis());
		CalendarUtils.checkNotOverlaps(interval, events);
	}

	@Test(expected = InvalidStartAndEndDateException.class)
	public void testOverlapsWithMultipleEvent() {
		Event event = new Event();
		EventDateTime start = new EventDateTime();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.AM_PM, Calendar.AM);
		startCalendar.set(Calendar.HOUR_OF_DAY, 1);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		DateTime startDateTime = new DateTime(startCalendar.getTime());
		start.setDate(startDateTime);
		event.setStart(start);

		EventDateTime end = new EventDateTime();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.AM_PM, Calendar.AM);
		endCalendar.set(Calendar.HOUR_OF_DAY, 8);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		DateTime endDateTime = new DateTime(endCalendar.getTime());
		end.setDate(endDateTime);
		event.setEnd(end);
		event.setSummary("\u6625\u5047");
		events.add(event);

		Event event1 = new Event();
		startCalendar.add(Calendar.DATE, 1);
		endCalendar.add(Calendar.DATE, 1);
		startDateTime = new DateTime(startCalendar.getTime());
		start = new EventDateTime();
		start.setDate(startDateTime);
		endDateTime = new DateTime(endCalendar.getTime());
		end = new EventDateTime();
		end.setDate(endDateTime);
		event1.setSummary("\u6625\u5047");
		event1.setStart(start);
		event1.setEnd(end);
		events.add(event1);
		Interval interval = new Interval(startCalendar.getTimeInMillis(),
				endCalendar.getTimeInMillis());
		CalendarUtils.checkNotOverlaps(interval, events);
	}

	@Test(expected = InvalidStartAndEndDateException.class)
	public void testOverlapsWithLargeInterval() {
		EventDateTime start = new EventDateTime();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.AM_PM, Calendar.AM);
		startCalendar.set(Calendar.HOUR_OF_DAY, 2);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		DateTime startDateTime = new DateTime(startCalendar.getTime());
		start.setDate(startDateTime);

		EventDateTime end = new EventDateTime();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.AM_PM, Calendar.AM);
		endCalendar.set(Calendar.HOUR_OF_DAY, 8);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		DateTime endDateTime = new DateTime(endCalendar.getTime());
		end.setDate(endDateTime);

		Event event1 = new Event();
		event1.setSummary("\u6625\u5047");
		event1.setStart(start);
		event1.setEnd(end);
		events.add(event1);
		startCalendar.set(Calendar.HOUR_OF_DAY, 1);
		endCalendar.set(Calendar.HOUR_OF_DAY, 9);
		Interval interval = new Interval(startCalendar.getTimeInMillis(),
				endCalendar.getTimeInMillis());
		CalendarUtils.checkNotOverlaps(interval, events);
	}

	@Test(expected = InvalidStartAndEndDateException.class)
	public void testOverlapsWithSmallInterval() {
		EventDateTime start = new EventDateTime();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.HOUR_OF_DAY, 2);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		DateTime startDateTime = new DateTime(startCalendar.getTime());
		start.setDate(startDateTime);

		EventDateTime end = new EventDateTime();
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.HOUR_OF_DAY, 8);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);
		DateTime endDateTime = new DateTime(endCalendar.getTime());
		end.setDate(endDateTime);

		Event event1 = new Event();
		event1.setSummary("\u6625\u5047");
		event1.setStart(start);
		event1.setEnd(end);
		events.add(event1);
		startCalendar.set(Calendar.HOUR_OF_DAY, 3);
		endCalendar.set(Calendar.HOUR_OF_DAY, 7);
		Interval interval = new Interval(startCalendar.getTimeInMillis(),
				endCalendar.getTimeInMillis());
		CalendarUtils.checkNotOverlaps(interval, events);
	}

	@Test
	public void testCountDurationHalfDay() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.HOUR_OF_DAY, 9);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.HOUR_OF_DAY, 13);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);

		double duration = CalendarUtils.countDuration(startCalendar.getTime(),
				endCalendar.getTime());
		assertEquals(0.5d, duration, 0.01d);
	}

	@Test
	public void testCountDurationFullDay() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.HOUR_OF_DAY, 8);
		startCalendar.set(Calendar.MINUTE, 30);
		startCalendar.set(Calendar.SECOND, 0);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.HOUR_OF_DAY, 13);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);

		double duration = CalendarUtils.countDuration(startCalendar.getTime(),
				endCalendar.getTime());
		assertEquals(1d, duration, 0.01d);
	}

	@Test
	public void testCountDurationLargeFullDay() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.HOUR_OF_DAY, 8);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.HOUR_OF_DAY, 20);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);

		double duration = CalendarUtils.countDuration(startCalendar.getTime(),
				endCalendar.getTime());
		assertEquals(1d, duration, 0.01d);
	}

	@Test
	public void testCountDurationCrossDay() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.HOUR_OF_DAY, 14);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.DAY_OF_YEAR, 1);
		endCalendar.set(Calendar.HOUR_OF_DAY, 14);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);

		double duration = CalendarUtils.countDuration(startCalendar.getTime(),
				endCalendar.getTime());
		assertEquals(1d, duration, 0.01d);
	}

	@Test
	public void testCountDurationThreeAndHalfDay() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.HOUR_OF_DAY, 10);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.DAY_OF_YEAR, 3);
		endCalendar.set(Calendar.HOUR_OF_DAY, 14);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);

		double duration = CalendarUtils.countDuration(startCalendar.getTime(),
				endCalendar.getTime());
		assertEquals(3.5d, duration, 0.01d);
	}

	@Test
	public void testCountDurationHalfAndThreeDay() {
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.HOUR_OF_DAY, 14);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.DAY_OF_YEAR, 3);
		endCalendar.set(Calendar.HOUR_OF_DAY, 18);
		endCalendar.set(Calendar.MINUTE, 0);
		endCalendar.set(Calendar.SECOND, 0);

		double duration = CalendarUtils.countDuration(startCalendar.getTime(),
				endCalendar.getTime());
		assertEquals(3.5d, duration, 0.01d);
	}

	@Test
	public void testOverDateOfJoinedFalse() {
		Calendar joinCalendar = Calendar.getInstance();
		joinCalendar.set(2011, 4, 5, 0, 0);

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(2016, 4, 4, 10, 0);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(2016, 4, 4, 18, 0);

		assertFalse(CalendarUtils.overDateOfJoined(startCalendar.getTime(),
				endCalendar.getTime(), joinCalendar.getTime()));
	}
	
	@Test
	public void testOverDateOfJoinedTrue() {
		Calendar joinCalendar = Calendar.getInstance();
		joinCalendar.set(2011, 4, 5, 0, 0);

		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(2014, 12, 31, 10, 0);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(2015, 4, 6, 18, 0);

		assertTrue(CalendarUtils.overDateOfJoined(startCalendar.getTime(),
				endCalendar.getTime(), joinCalendar.getTime()));
		
		startCalendar.set(2014, 12, 29, 10, 0);
		joinCalendar.set(2011, 12, 31, 0, 0);
		assertTrue(CalendarUtils.overDateOfJoined(startCalendar.getTime(),
				endCalendar.getTime(), joinCalendar.getTime()));
	}
}
