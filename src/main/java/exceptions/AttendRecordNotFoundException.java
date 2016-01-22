package exceptions;

public class AttendRecordNotFoundException extends HTTPNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AttendRecordNotFoundException() {
		super("AttendRecord could not be found.");
	}

	public AttendRecordNotFoundException(long id) {
		super(String.format("AttendRecord %s could not be found.", id));
	}

}
