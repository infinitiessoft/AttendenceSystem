package util;

import entity.AttendRecord;

public interface MailWritter {

	public abstract String buildSubject(AttendRecord record);

	public abstract String buildBody(AttendRecord record);

}