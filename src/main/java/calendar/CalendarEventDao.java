package calendar;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;

import resources.specification.CalendarEventSpecification;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.ColorDefinition;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class CalendarEventDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CalendarEventDao.class);
	/** Application name. */
	private static final String APPLICATION_NAME = "Attendance System2.0 with Google Calendar API";

	private static final String SERVICE_ACCOUNT_EMAIL = "attendance@attendance-1183.iam.gserviceaccount.com";

	/** Directory to store user credentials for this application. */
	// private static final java.io.File DATA_STORE_DIR = new java.io.File(
	// ".credentials/attendance");

	// /** Global instance of the {@link FileDataStoreFactory}. */
	// private static FileDataStoreFactory DATA_STORE_FACTORY;

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
			// DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			logger.warn("fail to access google calendar", t);
		}
	}
	private final String calendarId;

	public CalendarEventDao(String calendarId) {
		this.calendarId = calendarId;
	}

	public CalendarEventDao() {
		this("attendance@infinitiessoft.com");
	}

	public static void main(String args[]) throws IOException,
			GeneralSecurityException {
		CalendarEventDao dao = new CalendarEventDao();
		dao.findCalendars();
//		List<Event> events = dao.findAll(null, null);
//		for (Event event : events) {
//			System.err.println(event.getColorId() + "  " + event.getSummary()
//					+ "  " + event.getStatus() + "  " + event.getOrganizer()
//					+ "  " + event.getKind() + "  ");
//		}
		// System.err.println(es.size());
		// Event event = new Event();
		// EventDateTime start = new EventDateTime();
		// start.setDateTime(new DateTime(new Date()));
		// event.setStart(start);
		// event.setEnd(start);
		// event.setSummary("demo");
		// dao.save(event);
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	private Credential authorize() throws GeneralSecurityException, IOException {
		// Load client secrets.

		GoogleCredential credential = new GoogleCredential.Builder()
				.setTransport(HTTP_TRANSPORT)
				.setJsonFactory(JSON_FACTORY)
				.setServiceAccountUser("attendance@infinitiessoft.com")
				.setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
				.setServiceAccountScopes(SCOPES)
				.setServiceAccountPrivateKeyFromP12File(
						new File(
								"/Users/pohsun/Documents/workspace/attendance2.0/AttendenceSystem/src/main/resource/attendance-174d40e80c26.p12"))
				.build();
		credential.refreshToken();
		return credential;
	}

	/**
	 * Build and return an authorized Calendar client service.
	 * 
	 * @return an authorized Calendar client service
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	private com.google.api.services.calendar.Calendar getCalendarService()
			throws IOException, GeneralSecurityException {
		Credential credential = authorize();
		return new com.google.api.services.calendar.Calendar.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(
				APPLICATION_NAME).build();
	}

	public List<Event> findAll(CalendarEventSpecification spec,
			Pageable pageable) {
		try {
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
				java.util.Iterator<Order> iterator = pageable.getSort()
						.iterator();
				if (iterator != null && iterator.hasNext()) {
					orderBy = iterator.next().getProperty();
				}
			}
			logger.debug("timeMin:{}, timeMax:{}", new Object[] { timeMin,
					timeMax });

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
		} catch (IOException e) {
			throw new RuntimeException("calendar api error", e);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException("calendar api authentication error", e);
		}
	}

	public void findColors() throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.
		// Note: Do not confuse this class with the
		// com.google.api.services.calendar.model.Calendar class.
		com.google.api.services.calendar.Calendar service = getCalendarService();

		com.google.api.services.calendar.model.Colors colors = service.colors()
				.get().execute();

		// Print available calendar list entry colors
		for (Map.Entry<String, ColorDefinition> color : colors.getCalendar()
				.entrySet()) {
			System.out.println("ColorId : " + color.getKey());
			System.out.println("  Background: "
					+ color.getValue().getBackground());
			System.out.println("  Foreground: "
					+ color.getValue().getForeground());
		}

		// Print available event colors
		for (Map.Entry<String, ColorDefinition> color : colors.getEvent()
				.entrySet()) {
			System.out.println("ColorId : " + color.getKey());
			System.out.println("  Background: "
					+ color.getValue().getBackground());
			System.out.println("  Foreground: "
					+ color.getValue().getForeground());
		}
	}

	public void findCalendars() throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.
		// Note: Do not confuse this class with the
		// com.google.api.services.calendar.model.Calendar class.
		com.google.api.services.calendar.Calendar service = getCalendarService();

		CalendarList cs = service.calendarList().list().execute();

		// Print available calendar list entry colors
		for (CalendarListEntry c : cs.getItems()) {
			System.err.println(c.toPrettyString());
		}
	}

	public Event save(Event event) {
		try {
			com.google.api.services.calendar.Calendar service = getCalendarService();
			event = service.events().insert(calendarId, event).execute();
			logger.debug("Event created: {}", event.getHtmlLink());
			return event;
		} catch (IOException e) {
			throw new RuntimeException("calendar api error", e);
		} catch (GeneralSecurityException e) {
			throw new RuntimeException("calendar api authentication error", e);
		}
	}
}
