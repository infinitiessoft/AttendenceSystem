package exceptions;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

public class HTTPException extends SafeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String explanation;

	public HTTPException(final String message, final Response.Status status) {
		super(message, status);
		this.setExplanation(message);
	}

	public HTTPException(final Response.Status status) {
		this(null, status);
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	@Override
	public Map<String, List<Object>> getHeaders() {
		return this.getResponse().getHeaders();
	}

	@Override
	public int getCode() {
		return this.getResponse().getStatus();
	}

	@Override
	public boolean isSafe() {
		return false;
	}

}
