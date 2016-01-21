package exceptions;

import javax.ws.rs.core.Response;

public class ConflictException extends HTTPException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConflictException(String message) {
		super(message, Response.Status.CONFLICT);
	}

	public ConflictException(String dependencies, String entity) {
		this(
				String.format(
						"Threre are %s depends on this %s, ensure that deleted data is no longer referenced",
						dependencies, entity));
	}

}
