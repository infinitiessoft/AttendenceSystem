package exceptions;

import javax.ws.rs.core.Response;

public class InternalServerErrorException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InternalServerErrorException(String message) {
		super(message, Response.Status.INTERNAL_SERVER_ERROR);
	}

	public InternalServerErrorException() {
		this("There is a problem in system, please contact administrator");
	}
}
