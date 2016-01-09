package calendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.Duration;
import org.joda.time.Interval;

import com.google.api.services.calendar.model.Event;

import exceptions.InvalidStartAndEndDateException;

public class CalendarUtil {

	private final static String ADJUSTED_WORKING_DAY = "\u88dc\u73ed";

	private final static Interval WORKING_HOURS;

	static {
		Calendar start = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, 10);
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 18);
		WORKING_HOURS = new Interval(start.getTimeInMillis(),
				end.getTimeInMillis());
	}

	private CalendarUtil() {

	}

	public static void checkNotOverlaps(Interval interval, List<Event> events) {
		Set<Interval> adjustedWorkingDays = new HashSet<Interval>();

		// looking for adjusted working days in list
		for (Event event : events) {
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
			long eventStart = event.getStart() != null ? event.getStart()
					.getDate().getValue() : event.getStart().getDateTime()
					.getValue();
			long eventEnd = event.getEnd() != null ? event.getEnd().getDate()
					.getValue() : event.getEnd().getDateTime().getValue();
			Interval eventInterval = new Interval(eventStart, eventEnd);

			for (Interval removed : removedOverlaps) {
				if (removed.overlaps(eventInterval)) {
					String msg = String.format(
							"Invalid interval, it overlap %s %s", new Object[] {
									event.getSummary(), event.getStart() });
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

		Interval interval = new Interval(start.getTime(), end.getTime());
		double duration = 0d;

		if (interval.toPeriod().getHours() < 24) {
			// 4 hours is the minimum leave unit and can be count as 0.5 day
			// leave
			if (interval.toPeriod().getHours() > 4) {
				duration += 1d;
			} else {
				duration += 0.5d;
			}
		} else {
			duration = interval.toPeriod().getDays();
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
			if (diff.getStandardHours() <= 4) {
				duration -= 0.5d;
			}
			Calendar workingTime = Calendar.getInstance();
			workingTime.setTime(start);
			workingTime.set(Calendar.HOUR_OF_DAY, 10);
			workingTime.set(Calendar.MINUTE, 0);
			workingTime.set(Calendar.SECOND, 0);
			diff = new Duration(endC.getTimeInMillis(),
					workingTime.getTimeInMillis());

			// if there are less than 4 hours between the working
			// time(10:00) and end leave time then it can be count as a half
			if (diff.getStandardHours() <= 4) {
				duration -= 0.5d;
			}
		}

		return duration;
	}
}
