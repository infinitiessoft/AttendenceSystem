package exceptions;

public class AttendRecordTypeNotFoundException extends HTTPNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AttendRecordTypeNotFoundException() {
		super();
	}

	public AttendRecordTypeNotFoundException(long id) {
		super(String.format("AttendRecordType %s could not be found.", id));
	}

}
