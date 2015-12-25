package exceptions;

public class RoleNotFoundException extends HTTPNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoleNotFoundException() {
		super();
	}

	public RoleNotFoundException(long id) {
		super(String.format("Role %s could not be found.", id));
	}

}
