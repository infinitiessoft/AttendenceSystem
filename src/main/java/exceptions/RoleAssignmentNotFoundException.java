package exceptions;

public class RoleAssignmentNotFoundException extends HTTPNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoleAssignmentNotFoundException() {
		super();
	}

	public RoleAssignmentNotFoundException(long employeeId, long roleId) {
		super(String.format(
				"Employee %s and Role %s assignment could not be found.",
				employeeId, roleId));
	}

}
