package exceptions;

import javax.ws.rs.core.Response;

public class HTTPNotFoundException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HTTPNotFoundException(String message) {
		super(message, Response.Status.NOT_FOUND);
	}

	public HTTPNotFoundException() {
		this("The resource could not be found.");
	}
}
