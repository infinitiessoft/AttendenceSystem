package resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import exceptions.ErrorMessage;

@Service
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	private Logger logger = LoggerFactory
			.getLogger(GenericExceptionMapper.class);

	@Override
	public Response toResponse(Throwable ex) {
		logger.warn("catch exception", ex);
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setCode(Response.Status.BAD_REQUEST.getStatusCode());
		Throwable root = ExceptionUtils.getRootCause(ex);
		if (root == null) {
			root = ex;
		}
		String msg = root.getMessage();
		errorMessage.setMessage(msg);
		setHttpStatus(ex, errorMessage);
		return Response.status(errorMessage.getCode()).entity(errorMessage)
				.header("safe", true).type(MediaType.APPLICATION_JSON).build();
	}

	private void setHttpStatus(Throwable ex, ErrorMessage errorMessage) {
		if (ex instanceof WebApplicationException) {
			errorMessage.setCode(((WebApplicationException) ex).getResponse()
					.getStatus());
		} else if (ex instanceof org.springframework.dao.DataIntegrityViolationException) {
			errorMessage.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			String msg = ExceptionUtils.getRootCause(ex).getMessage();
			errorMessage.setMessage("invalid argument: " + msg);
		} else if (ex instanceof org.springframework.dao.EmptyResultDataAccessException) {
			errorMessage.setCode(Response.Status.NOT_FOUND.getStatusCode());
		} else if (ex instanceof org.springframework.security.access.AccessDeniedException) {
			errorMessage.setCode(Response.Status.UNAUTHORIZED.getStatusCode());
		} else {
			errorMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR
					.getStatusCode()); // defaults to internal server error 500
		}
	}
}
