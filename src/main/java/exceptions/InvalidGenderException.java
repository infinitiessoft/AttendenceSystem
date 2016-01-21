package exceptions;

public class InvalidGenderException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidGenderException(String gender) {
		super(String.format("invalid gender %s", gender));
	}

}
