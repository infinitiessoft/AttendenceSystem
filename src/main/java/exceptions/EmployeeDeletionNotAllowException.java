package exceptions;

public class EmployeeDeletionNotAllowException extends ForbiddenException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeDeletionNotAllowException() {
		super();
	}

	public EmployeeDeletionNotAllowException(long id) {
		super(String.format("The deletion is not allow, because there are employees response to employee %s.", id));
	}

}
