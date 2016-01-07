package exceptions;

public class InvalidStartAndEndDateException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidStartAndEndDateException() {
		super("Invalid startDate and endDate");
	}

}
