package exceptions;

public class EmployeeLeaveNotFoundException extends HTTPNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmployeeLeaveNotFoundException() {
		super();
	}

	public EmployeeLeaveNotFoundException(long id) {
		super(String.format("EmployeeLeave %s could not be found.", id));
	}

	public EmployeeLeaveNotFoundException(long employeeId, long leavesettingId) {
		super(
				String.format(
						"EmployeeLeave for employee %s to setting %s could not be found.",
						employeeId, leavesettingId));
	}

}
