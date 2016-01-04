package exceptions;

public class LeavesettingNotFoundException extends HTTPNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LeavesettingNotFoundException() {
		super();
	}

	public LeavesettingNotFoundException(long id) {
		super(String.format("Leavesetting %s could not be found.", id));
	}

}
