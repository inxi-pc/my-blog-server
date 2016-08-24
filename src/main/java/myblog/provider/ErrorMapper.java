package myblog.provider;

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
        return Response.status(500)
                .entity(e)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
