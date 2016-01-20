package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.Duration;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Event.Organizer;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

import entity.AttendRecord;
import entity.Employee;
import exceptions.InvalidStartAndEndDateException;

public class CalendarUtils {

	private final static Logger logger = LoggerFactory
			.getLogger(CalendarUtils.class);
	private final static String ADJUSTED_WORKING_DAY = "\u88dc\u73ed";
	private static final String COLOR_ID = "4";
	private static final String CONFIRMED = "confirmed";

	private CalendarUtils() {

	}

	public static void checkNotOverlaps(Interval interval, List<Event> events) {
		Set<Interval> adjustedWorkingDays = new HashSet<Interval>();

		// looking for adjusted working days in list
		for (Event event : events) {
			logger.debug("event: {} ({})", new Object[] { event.getSummary(),
					event.getStart() });
			if (event.getSummary().contains(ADJUSTED_WORKING_DAY)) {
				long eventStart = event.getStart() != null ? event.getStart()
						.getDate().getValue() : event.getStart().getDateTime()
						.getValue();
				long eventEnd = event.getEnd() != null ? event.getEnd()
						.getDate().getValue() : event.getEnd().getDateTime()
						.getValue();
				Interval eventInterval = new Interval(eventStart, eventEnd);
				adjustedWorkingDays.add(eventInterval);
			}
		}

		// remove adjusted working days overlaps from interval
		List<Interval> removedOverlaps = new ArrayList<Interval>();
		removedOverlaps.add(interval);
		List<Interval> temp = null;
		for (Interval awd : adjustedWorkingDays) {
			Iterator<Interval> iterator = removedOverlaps.iterator();
			while (iterator.hasNext()) {
				Interval next = iterator.next();
				temp = difference(next, awd);
				iterator.remove();
			}
			removedOverlaps.addAll(temp);
		}

		for (Event event : events) {
			if (COLOR_ID.equals(event.getColorId())) { // ignore leave event
				continue;
			}
			long eventStart = event.getStart().getDate() != null ? event
					.getStart().getDate().getValue() : event.getStart()
					.getDateTime().getValue();
			long eventEnd = event.getEnd().getDate() != null ? event.getEnd()
					.getDate().getValue() : event.getEnd().getDateTime()
					.getValue();
			Interval eventInterval = new Interval(eventStart, eventEnd);

			for (Interval removed : removedOverlaps) {
				if (removed.overlaps(eventInterval)) {
					DateTime date = event.getStart().getDate() != null ? event
							.getStart().getDate() : event.getStart()
							.getDateTime();
					String msg = String.format(
							"Invalid interval, it overlap %s(%s)",
							new Object[] { event.getSummary(), date });
					throw new InvalidStartAndEndDateException(msg);
				}
			}
		}
	}

	private static List<Interval> difference(Interval a, Interval b) {
		List<Interval> removed = new ArrayList<Interval>();
		if (a.overlaps(b)) {
			if (a.getStartMillis() < b.getStartMillis()) {
				removed.add(new Interval(a.getStartMillis(), b.getStartMillis()));
			}

			if (a.getEndMillis() > b.getEndMillis()) {
				removed.add(new Interval(b.getEndMillis(), a.getEndMillis()));
			}
		} else {
			removed.add(a);
		}
		return removed;
	}

	public static double countDuration(Date start, Date end) {
		Calendar startC = Calendar.getInstance();
		startC.setTime(start);
		Calendar endC = Calendar.getInstance();
		endC.setTime(end);
		logger.debug("start:{}, end:{}", new Object[] { start, end });
		long days = getDaysInvolve(startC, endC);
		Interval interval = new Interval(start.getTime(), end.getTime());
		logger.debug("interval days:{}, hours:{}", new Object[] { days,
				interval.toPeriod().getHours() });
		double duration = 0d;

		Duration halfDay = Duration.standardMinutes(241);
		if (days <= 1) {
			// 4 hours is the minimum leave unit and can be count as 0.5 day
			// leave
			if (interval.toDuration().isLongerThan(halfDay)) {
				logger.debug("full day");
				duration += 1d;
			} else {
				logger.debug("half day");
				duration += 0.5d;
			}
		} else {
			duration = days;
			Calendar closingTime = Calendar.getInstance();
			closingTime.setTime(start);
			closingTime.set(Calendar.HOUR_OF_DAY, 18);
			closingTime.set(Calendar.MINUTE, 0);
			closingTime.set(Calendar.SECOND, 0);

			Duration diff = new Duration(startC.getTimeInMillis(),
					closingTime.getTimeInMillis());
			// if there are less than 4 hours between the closing
			// time(18:00) and start leave time then it can be count as a half
			// day leave
			if (diff.isShorterThan(halfDay)) {
				duration -= 0.5d;
			}
			Calendar workingTime = Calendar.getInstance();
			workingTime.setTime(end);
			workingTime.set(Calendar.HOUR_OF_DAY, 10);
			workingTime.set(Calendar.MINUTE, 0);
			workingTime.set(Calendar.SECOND, 0);
			diff = new Duration(workingTime.getTimeInMillis(),
					endC.getTimeInMillis());

			// if there are less than 4 hours between the working
			// time(10:00) and end leave time then it can be count as a half
			if (diff.isShorterThan(halfDay)) {
				duration -= 0.5d;
			}
		}
		logger.debug("duration:{}", duration);
		return duration;
	}

	private static long getDaysInvolve(Calendar startDate, Calendar endDate) {
		if (startDate.after(endDate)) {
			throw new IllegalArgumentException(
					"End date should always greater than start date.");
		}
		Calendar date = (Calendar) startDate.clone();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);

		Calendar end = (Calendar) endDate.clone();
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		long daysBetween = 1;
		while (end.get(Calendar.YEAR) != date.get(Calendar.YEAR)
				|| end.get(Calendar.DAY_OF_YEAR) != date
						.get(Calendar.DAY_OF_YEAR)) {
			date.add(Calendar.DAY_OF_YEAR, 1);
			daysBetween++;
		}
		return daysBetween;
	}

	public static Event toEvent(AttendRecord record) {
		Event event = new Event();
		EventDateTime start = new EventDateTime();
		start.setDateTime(new DateTime(record.getStartDate()));
		event.setStart(start);
		EventDateTime end = new EventDateTime();
		end.setDateTime(new DateTime(record.getEndDate()));
		event.setEnd(end);
		event.setStatus(CONFIRMED);
		Employee employee = record.getEmployee();
		Organizer organizer = new Organizer();
		organizer.setDisplayName(String.format("%s(%s)", employee.getName(),
				employee.getUsername()));
		organizer.setEmail(employee.getEmail());
		event.setOrganizer(organizer);
		String title = String.format("%s %s(%s)", record.getType().getName(),
				employee.getName(), employee.getUsername());
		event.setSummary(title);
		event.setColorId(COLOR_ID);
		event.setDescription(record.getReason());

		EventAttendee[] attendees = new EventAttendee[] { new EventAttendee()
				.setEmail(employee.getEmail()) };
		event.setAttendees(Arrays.asList(attendees));
		return event;
	}

	public static boolean overDateOfJoined(Date start, Date end, Date join) {
		Calendar startC = Calendar.getInstance();
		startC.setTime(start);
		Calendar endC = Calendar.getInstance();
		endC.setTime(end);
		Calendar joinC = Calendar.getInstance();
		joinC.setTime(join);
		if (joinC.get(Calendar.DAY_OF_YEAR) >= startC.get(Calendar.DAY_OF_YEAR)
				&& joinC.get(Calendar.DAY_OF_YEAR) <= endC
						.get(Calendar.DAY_OF_YEAR)) {
			return true;
		}
		return false;
	}

}
