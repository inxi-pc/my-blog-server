package myblog.provider;

import myblog.App;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ErrorMapper implements javax.ws.rs.ext.ExceptionMapper<Error> {

    public Response toResponse(Error e) {
        if (App.isDebug()) {
            e.printStackTrace();
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
