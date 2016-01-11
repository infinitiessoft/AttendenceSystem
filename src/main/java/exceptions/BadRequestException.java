package exceptions;

import javax.ws.rs.core.Response;

public class BadRequestException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
		super(message, Response.Status.BAD_REQUEST);
	}

	public BadRequestException() {
		this("Invalid arguments.");
	}
}
