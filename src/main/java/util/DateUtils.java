package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {

	private final static Logger logger = LoggerFactory
			.getLogger(DateUtils.class);
	private final static String FULL = "yyyy-MM-dd'T'hh:mm:ss.SSS'Z'";
	private final static String TO_SECONDS = "yyyy-MM-dd'T'hh:mm:ss'Z'";
	private final static String TO_MINUTES = "yyyy-MM-dd'T'hh:mm";
	private final static String TO_DAYS = "yyyy-MM-dd";

	private final static String FULL2 = "yyyy-MM-dd hh:mm:ss.SSS";
	private final static String TO_SECONDS2 = "yyyy-MM-dd hh:mm:ss";
	private final static String TO_MINUTES2 = "yyyy-MM-dd hh:mm";

	public static void main(String args[]) throws ParseException {
		DateFormat formatFull = new SimpleDateFormat(TO_MINUTES2);
		System.err.println(formatFull.parse("2015-01-01 01:00"));
	}

	private DateUtils() {

	}

	@SuppressWarnings("deprecation")
	public static Date parseString(String text) {

		DateFormat formatFull = new SimpleDateFormat(FULL);
		DateFormat formatToSeconds = new SimpleDateFormat(TO_SECONDS);
		DateFormat formatToMinutes = new SimpleDateFormat(TO_MINUTES);
		DateFormat formatToDays = new SimpleDateFormat(TO_DAYS);
		DateFormat formatFull2 = new SimpleDateFormat(FULL2);
		DateFormat formatToSeconds2 = new SimpleDateFormat(TO_SECONDS2);
		DateFormat formatToMinutes2 = new SimpleDateFormat(TO_MINUTES2);

		Map<String, DateFormat> formats = new HashMap<String, DateFormat>();
		formats.put(FULL, formatFull);
		formats.put(TO_SECONDS, formatToSeconds);
		formats.put(TO_MINUTES, formatToMinutes);
		formats.put(TO_DAYS, formatToDays);
		formats.put(FULL2, formatFull2);
		formats.put(TO_SECONDS2, formatToSeconds2);
		formats.put(TO_MINUTES2, formatToMinutes2);

		try {
			return new Date(text);
		} catch (Exception e) {
			logger.debug("fail parse:{} in Constructor", text);
		}

		for (Entry<String, DateFormat> format : formats.entrySet()) {
			try {
				Date date = format.getValue().parse(text);
				logger.debug("success parse:{} in format:{}", new Object[] {
						text, format.getKey() });
				return date;
			} catch (Exception e) {
				logger.debug("fail parse:{} in format:{}", new Object[] { text,
						format.getKey() });
			}
		}
		return null;
	}

}
