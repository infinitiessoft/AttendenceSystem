package resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import exceptions.ErrorMessage;
import exceptions.SafeException;

@Service
@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	private Logger logger = LoggerFactory
			.getLogger(GenericExceptionMapper.class);

	@Override
	public Response toResponse(Throwable ex) {
		logger.warn("catch exception", ex);
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMessage(ex.getMessage());
		setHttpStatus(ex, errorMessage);
		boolean safe = ex instanceof SafeException;
		return Response.status(errorMessage.getCode()).entity(errorMessage)
				.header("safe", safe).type(MediaType.APPLICATION_JSON).build();
	}

	private void setHttpStatus(Throwable ex, ErrorMessage errorMessage) {
		if (ex instanceof WebApplicationException) {
			errorMessage.setCode(((WebApplicationException) ex).getResponse()
					.getStatus());
		} else if (ex instanceof javax.persistence.PersistenceException) {
			errorMessage.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			errorMessage.setMessage("invalid argument");
		} else {
			errorMessage.setCode(Response.Status.INTERNAL_SERVER_ERROR
					.getStatusCode()); // defaults to internal server error 500
		}
	}
}
