package myblog.provider;

import myblog.exception.ExtendException;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapper implements ExtendedExceptionMapper<Exception> {

    public boolean isMappable(Exception exception) {
        return true;
    }

    public Response toResponse(Exception e) {
        if (e instanceof ExtendException) {
            return Response.status(((ExtendException) e).status)
                    .entity(e)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response.status(500)
                    .entity(e)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
