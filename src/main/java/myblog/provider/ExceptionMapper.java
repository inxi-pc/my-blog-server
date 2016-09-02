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
        // Convert all exception to extend exception
        ExtendException exException;
        if (e instanceof ExtendException){
            exException = (ExtendException) e;
        } else if (e instanceof WebApplicationException) {
            int status = ((WebApplicationException) e).getResponse().getStatus();
            exException = new ExtendException(status, e);
        } else {
            exException = new ExtendException(500, e);
        }

        if (App.isDebug()) {
            return Response.status(exException.status)
                    .entity(exException)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response.status(exException.status)
                    .entity(exException.toResponseEntity())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
