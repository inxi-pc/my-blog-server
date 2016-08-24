package myblog.provider;

import myblog.exception.ExtendException;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ExtendExceptionMapper implements ExtendedExceptionMapper<ExtendException> {

    public boolean isMappable(ExtendException exception) {
        return true;
    }

    public Response toResponse(ExtendException e) {
        return Response.status(e.status)
                .entity(e)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
