package exceptions;

import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public abstract class SafeException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SafeException(final String message, final Response.Status status) {
		super(message, status);
	}

	public abstract Map<String, List<Object>> getHeaders();

	public abstract int getCode();

	public abstract boolean isSafe();

}
