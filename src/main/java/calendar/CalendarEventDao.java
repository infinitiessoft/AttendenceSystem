package calendar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import resources.specification.CalendarEventSpecification;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class CalendarEventDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CalendarEventDao.class);
	/** Application name. */
	private static final String APPLICATION_NAME = "Attendance System2.0 with Google Calendar API";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(
			".credentials/attendance");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory
			.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/** Global instance of the scopes required by this quickstart. */
	private static final List<String> SCOPES = Arrays
			.asList(CalendarScopes.CALENDAR);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			logger.warn("fail to access google calendar", t);
		}
	}
	private final String calendarId;

	public CalendarEventDao(String calendarId) {
		this.calendarId = calendarId;
	}

	public CalendarEventDao() {
		this("primary");
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	private Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = new File(
				"/Users/pohsun/Documents/workspace/attendance2.0/AttendenceSystem/src/main/resource/client_secret.json")
				.toURL().openStream();
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
				JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
				.setDataStoreFactory(DATA_STORE_FACTORY)
				.setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow,
				new LocalServerReceiver()).authorize("user");
		logger.debug("Credentials saved to {}",
				DATA_STORE_DIR.getAbsolutePath());
		return credential;
	}

	/**
	 * Build and return an authorized Calendar client service.
	 * 
	 * @return an authorized Calendar client service
	 * @throws IOException
	 */
	private com.google.api.services.calendar.Calendar getCalendarService()
			throws IOException {
		Credential credential = authorize();
		return new com.google.api.services.calendar.Calendar.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(
				APPLICATION_NAME).build();
	}

	public List<Event> findAll(CalendarEventSpecification spec,
			Pageable pageable) throws IOException {
		// Build a new authorized API client service.
		// Note: Do not confuse this class with the
		// com.google.api.services.calendar.model.Calendar class.
		com.google.api.services.calendar.Calendar service = getCalendarService();

		// List the next 10 events from the primary calendar.
		DateTime timeMin = null;
		if (spec != null && spec.getTimeMin() != null) {
			timeMin = new DateTime(spec.getTimeMin());
		}

		DateTime timeMax = null;
		if (spec != null && spec.getTimeMax() != null) {
			timeMax = new DateTime(spec.getTimeMax());
		}

		String orderBy = "startTime";
		if (pageable != null && pageable.getSort() != null) {
			java.util.Iterator<Order> iterator = pageable.getSort().iterator();
			if (iterator != null && iterator.hasNext()) {
				orderBy = iterator.next().getProperty();
			}
		}

		String pageToken = null;
		List<Event> eventList = new ArrayList<Event>();
		do {
			com.google.api.services.calendar.Calendar.Events.List list = service
					.events().list(calendarId).setPageToken(pageToken)
					.setOrderBy(orderBy).setSingleEvents(true);
			if (timeMin != null) {
				list.setTimeMin(timeMin);
			}
			if (timeMax != null) {
				list.setTimeMax(timeMax);
			}
			Events events = list.execute();
			eventList.addAll(events.getItems());
			pageToken = events.getNextPageToken();
		} while (pageToken != null);

		return eventList;
	}

	public Event save(Event event) throws IOException {
		com.google.api.services.calendar.Calendar service = getCalendarService();
		event = service.events().insert(calendarId, event).execute();
		logger.debug("Event created: {}", event.getHtmlLink());
		return event;
	}
}
