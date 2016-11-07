package myblog.provider;

import com.fasterxml.jackson.databind.JsonMappingException;
import myblog.App;
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
            ex = new BadRequestException(e.getMessage(), e);
        } else {
            ex = new InternalServerErrorException(e.getMessage(), e);
        }

        if (App.isDebug()) {
            e.printStackTrace();
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
