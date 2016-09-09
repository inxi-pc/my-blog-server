package myblog.provider;

import myblog.App;
import org.glassfish.jersey.spi.ExtendedExceptionMapper;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class ErrorMapper implements ExtendedExceptionMapper<Error> {

    public boolean isMappable(Error exception) {
        return true;
    }

    public Response toResponse(Error e) {
        if (App.isDebug()) {
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
