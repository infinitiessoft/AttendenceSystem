package exceptions;

public class ModificationNotAllowException extends ForbiddenException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ModificationNotAllowException() {
		super("The modification is not allow, because AttendRecord was approved.");
	}

	public ModificationNotAllowException(long id) {
		super(String.format("The modification is not allow, because AttendRecord %s was approved.", id));
	}

}
