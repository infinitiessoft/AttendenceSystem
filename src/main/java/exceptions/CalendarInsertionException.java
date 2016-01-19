package exceptions;

public class CalendarInsertionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CalendarInsertionException(String message, Throwable t) {
		super(message, t);
	}

}
