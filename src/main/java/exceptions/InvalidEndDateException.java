package exceptions;

public class InvalidEndDateException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidEndDateException() {
		super("EndDate have to after StartDate");
	}

}
