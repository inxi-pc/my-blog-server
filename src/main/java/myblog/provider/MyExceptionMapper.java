package myblog.provider;

import com.fasterxml.jackson.databind.JsonMappingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import myblog.App;
import myblog.exception.HttpExceptionFactory;
import org.apache.logging.log4j.Level;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionMapper implements ExceptionMapper<Exception> {

    public Response toResponse(Exception e) {
        App.logger.log(Level.ERROR, e);

        WebApplicationException ex;
        if (e instanceof WebApplicationException) {
            ex = (WebApplicationException) e;
        } else if (e instanceof JsonMappingException){
            ex = HttpExceptionFactory.produce(BadRequestException.class, e);
        } else if (e instanceof JwtException) {
            if (e instanceof ExpiredJwtException) {
                ex = HttpExceptionFactory.produce(
                        WebApplicationException.class,
                        Response.Status.UNAUTHORIZED,
                        HttpExceptionFactory.Type.AUTHENTICATE_FAILED,
                        HttpExceptionFactory.Reason.BEARER_TOKEN_EXPIRED);
            } else {
                ex = HttpExceptionFactory.produce(
                        WebApplicationException.class,
                        Response.Status.UNAUTHORIZED,
                        HttpExceptionFactory.Type.AUTHENTICATE_FAILED,
                        HttpExceptionFactory.Reason.INVALID_BEARER_TOKEN);
            }
        } else {
            ex = HttpExceptionFactory.produce(InternalServerErrorException.class, e);
        }

        if (App.isDebug()) {
            e.printStackTrace();
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
