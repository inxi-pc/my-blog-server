package myblog.provider;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.jsonwebtoken.JwtException;
import myblog.App;
import myblog.exception.GenericException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper extends ThrowableMapper<Exception> {

    public Response toResponse(Exception e) {
        logger.error(e.getMessage(), e);

        WebApplicationException ex;
        if (e instanceof WebApplicationException) {
            ex = (WebApplicationException) e;
        } else if (e instanceof JsonMappingException) {
            ex = new BadRequestException(e.getMessage(), e);
        } else if (e instanceof JwtException) {
            ex = new WebApplicationException(e, Response.Status.UNAUTHORIZED);
        } else if (e instanceof GenericException) {
            ex = new WebApplicationException(e.getMessage(), ((GenericException) e).getStatus());
        } else {
            ex = new InternalServerErrorException(e.getMessage(), e);
        }

        if (App.isDebug()) {
            return Response.status(ex.getResponse().getStatus())
                    .entity(ex)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } else {
            return Response.status(ex.getResponse().getStatus())
                    .entity(ex.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
