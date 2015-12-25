package exceptions;

public class DepartmentNotFoundException extends HTTPNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DepartmentNotFoundException() {
		super();
	}

	public DepartmentNotFoundException(long id) {
		super(String.format("Department %s could not be found.", id));
	}

}
