package myblog.provider;

import myblog.App;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Exception> {

    public Response toResponse(Exception e) {
        // Convert all exception to WebApplicationException
        WebApplicationException ex;
        if (e instanceof WebApplicationException) {
            ex = (WebApplicationException) e;
        } else {
            ex = new InternalServerErrorException(e);
        }

        if (App.isDebug()) {
            ex.printStackTrace();
            return Response.status(ex.getResponse().getStatus())
                    .entity(ex)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response.status(ex.getResponse().getStatus())
                    .entity(ex.getResponse().getEntity())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
