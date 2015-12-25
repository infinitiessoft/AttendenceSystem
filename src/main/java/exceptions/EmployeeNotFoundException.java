package exceptions;

public class EmployeeNotFoundException extends HTTPNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeNotFoundException() {
		super();
	}

	public EmployeeNotFoundException(long id) {
		super(String.format("Employee %s could not be found.", id));
	}

}
