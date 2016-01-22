package util;

import java.util.Date;

import entity.AttendRecord;

public class MailUtils {

	private final static String header = "Please note: This mail is the system automatic transmission, please not direct reply.\n";
	private final static String footer = "The general outline of The Infinities Software Company Leave Policy V201104:\n"
			+ "1.When an employee is absent because of the need must submit a request for leave. If an employee is absent for a continuous period of more than 3 consecutive days or on more than 6 occasions in a month without obtaining approval for leave will be regarded as being absent without permission and are liable to have their employment terminated.\n"
			+ "2.All employee who require leave should obtain approval by their manager in advance. If it is not possible to notify the manager in advance of taking leave, the employee must contact their manager for request and apply for the leave afterward.\n"
			+ "3.For leave that is less than a full day, the leave should be approved by your Line Manager. For periods in excess of 1 day, the leave must be approved at Senior Management level.\n"
			+ "4.Any employee who falsely or fraudulently requests leave to which he is provides false or fraudulent documents is subject to appropriate disciplinary action including cancel the leave request and employment termination.\n"
			+ "5.After the leave request is approved and posted, it can still be changed by the employee. Employees will need to make the applicable changes and re-submit it for approval again.\n";
	private final static String ATTENDANCE_SYSTEM_URL = "http://192.168.0.148:8080/attendance";

	private MailUtils() {

	}

	public static String buildSubject(AttendRecord record) {
		String name = record.getEmployee().getName();
		String type = record.getType().getName();
		String subject = name + " applied for " + type + " Leaves notice";
		return subject;
	}

	public static String buildBody(AttendRecord record) {
		String name = record.getEmployee().getName();
		String type = record.getType().getName();
		Date start = record.getStartDate();
		Date end = record.getEndDate();
		Double duration = record.getDuration();
		String reason = record.getReason();

		String body = header
				+ "\n"
				+ name
				+ " applied for "
				+ type
				+ ", please confirm it in the attendance system: "
				+ ATTENDANCE_SYSTEM_URL
				+ "\n"
				+ "Start Date : "
				+ start
				+ "\nEnd Date : "
				+ end
				+ " \n"
				+ duration
				+ "days\nReason : "
				+ reason
				+ "\n--------------------------------------------------------------------\n"
				+ footer;
		return body;
	}

}
