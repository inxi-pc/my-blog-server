package myblog.provider;

import myblog.App;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class MyErrorMapper extends ThrowableMapper<Error> {

    public Response toResponse(Error e) {
        logger.error(e.getMessage(), e);

        if (App.isDebug()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
