package exceptions;

public class InvalidActionException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidActionException(String action) {
		super(String.format("invalid action %s", action));
	}

}
