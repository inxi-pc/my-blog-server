package myblog.provider;

import myblog.App;
import myblog.exception.ExtendException;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapper implements ExtendedExceptionMapper<Exception> {

    public boolean isMappable(Exception exception) {
        return true;
    }

    public Response toResponse(Exception e) {
        if (e instanceof WebApplicationException) {
            return ((WebApplicationException) e).getResponse();
        } else if (e instanceof ExtendException) {
            if (App.isDebugModel()) {
                return Response.status(((ExtendException) e).status)
                        .entity(e)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } else {
                return Response.status(((ExtendException) e).status)
                        .entity(((ExtendException) e).message)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }

        } else {
            if (App.isDebugModel()) {
                return Response.status(500)
                        .entity(e)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            } else {
                return Response.status(500)
                        .entity(e.getMessage())
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
        }
    }
}
