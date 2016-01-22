package exceptions;

import javax.ws.rs.core.Response;

public class ForbiddenException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
		super(message, Response.Status.FORBIDDEN);
	}

	public ForbiddenException() {
		this("Forbidden");
	}
}
