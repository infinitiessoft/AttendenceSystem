package assertion;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import exceptions.ErrorMessage;

public class AssertUtils {

	private AssertUtils() {

	}

	public static void assertNotFound(Response response) {
		assertSafeError(Status.NOT_FOUND.getStatusCode(), response);
	}

	public static void assertBadRequest(Response response) {
		assertSafeError(Status.BAD_REQUEST.getStatusCode(), response);
	}

	public static void assertSafeError(int code, Response response) {
		assertEquals(code, response.getStatus());
		ErrorMessage error = response.readEntity(ErrorMessage.class);
		assertEquals("true", response.getHeaderString("safe"));
		assertEquals(code, error.getCode());
	}

}
