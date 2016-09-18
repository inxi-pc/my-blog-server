package myblog.provider;

import myblog.App;
import myblog.exception.ExtendException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

    public Response toResponse(Exception e) {
        // Convert all exception to extend exception
        ExtendException ex;
        if (e instanceof ExtendException){
            ex = (ExtendException) e;
        } else if (e instanceof WebApplicationException) {
            int status = ((WebApplicationException) e).getResponse().getStatus();
            ex = new ExtendException(status, e);
        } else {
            ex = new ExtendException(500, e);
        }

        if (App.isDebug()) {
            e.printStackTrace();
            return Response.status(ex.status)
                    .entity(ex)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response.status(ex.status)
                    .entity(ex.toResponseEntity())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
