package exceptions;

public class JobTypeNotFoundException extends HTTPNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JobTypeNotFoundException() {
		super();
	}

	public JobTypeNotFoundException(long id) {
		super(String.format("Job %s could not be found.", id));
	}

}
