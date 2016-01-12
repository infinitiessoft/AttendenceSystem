package exceptions;

public class DuplicateApproveException extends BadRequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateApproveException() {
		super("Event have been approved or rejected");
	}

}
