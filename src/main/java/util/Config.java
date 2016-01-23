package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {

	private static final Logger logger = LoggerFactory.getLogger(Config.class);
	private static final String PROPERTIES_FILE = "system.properties";
	private final Properties properties = new Properties();
	public static final String GOOGLE_CALENDAR_ID = "google.calendar.id";
	public static final String PAGE_SIZE = "pageSize";
	public static final String MAIL_URL = "mail.url";
	public static final String MAIL_HEADER = "mail.header";
	public static final String MAIL_FOOTER = "mail.footer";
	public static final String GOOGLE_CALENDAR_CERTIFICATION = "google.calendar.certification";
	public static final String GOOGLE_CALENDAR_APPLICATION_NAME = "google.calendar.application.name";
	public static final String GOOGLE_CALENDAR_SERVICE_ACCOUNT_EMAIL = "google.calendar.service.account.email";
	public static final String GOOGLE_CALENDAR_ACCOUNT_USER = "google.calendar.account.user";

	private static Config instance = new Config();

	private Config() {
		String configFileLocation = PROPERTIES_FILE;
		try {
			properties.load(new FileInputStream(configFileLocation));
		} catch (FileNotFoundException e) {
			String message = configFileLocation + " file is not found";
			logger.warn(message, e);
			throw new RuntimeException(message, e);
		} catch (IOException e) {
			String message = "Error while reading" + configFileLocation
					+ " file";
			logger.warn(message, e);
			throw new RuntimeException(message, e);
		}
	}

	public static String getProperty(String key) {
		return instance.properties.getProperty(key);
	}

}
