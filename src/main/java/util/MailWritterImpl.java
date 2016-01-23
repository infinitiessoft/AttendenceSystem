package util;

import java.util.Date;

import entity.AttendRecord;

public class MailWritterImpl implements MailWritter {

	private String header;
	private String footer;
	private String systemUrl;

	public MailWritterImpl() {

	}

	/* (non-Javadoc)
	 * @see util.MailWritterI#buildSubject(entity.AttendRecord)
	 */
	@Override
	public String buildSubject(AttendRecord record) {
		String name = record.getEmployee().getName();
		String type = record.getType().getName();
		String subject = name + " applied for " + type + " Leaves notice";
		return subject;
	}

	/* (non-Javadoc)
	 * @see util.MailWritterI#buildBody(entity.AttendRecord)
	 */
	@Override
	public String buildBody(AttendRecord record) {
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
				+ systemUrl
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

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}

	public String getSystemUrl() {
		return systemUrl;
	}

	public void setSystemUrl(String systemUrl) {
		this.systemUrl = systemUrl;
	}

}
