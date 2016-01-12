package exceptions;

public class EventNotFoundException extends HTTPNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EventNotFoundException() {
		super();
	}

	public EventNotFoundException(long id) {
		super(String.format("Event %s could not be found.", id));
	}

}
