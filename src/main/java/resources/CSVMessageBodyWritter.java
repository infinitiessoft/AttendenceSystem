package resources;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Provider
@Produces({ CSVMessageBodyWritter.TEXT_CSV,
		CSVMessageBodyWritter.APPLICATION_EXCEL })
public class CSVMessageBodyWritter implements MessageBodyWriter<List<?>> {

	private static final Object FILENAME = "export.csv";
	public static final String TEXT_CSV = "text/csv; charset=utf-8";
	public static final String APPLICATION_EXCEL = "application/vnd.ms-excel";

	// private static final Object FILENAME = "result.csv";

	@Override
	public boolean isWriteable(Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		boolean ret = List.class.isAssignableFrom(type);
		return ret;
	}

	@Override
	public long getSize(List<?> t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType) {
		return -1;
	}

	@Override
	public void writeTo(List<?> t, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException,
			WebApplicationException {
		if (t != null && t.size() > 0) {
			httpHeaders.putSingle("Content-Disposition",
					"attachment; filename=\"" + FILENAME + "\"");
			Object o = t.get(0);
			CsvMapper mapper = new CsvMapper();
			CsvSchema schema = mapper.schemaFor(o.getClass()).withHeader();
			// OutputStreamWriter writerOutputStream = new OutputStreamWriter(
			// entityStream, "UTF_16BE");
			mapper.writer(schema).writeValue(entityStream, t);
		}

	}
}
