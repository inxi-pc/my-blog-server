package myblog.resource;

import myblog.domain.User;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.service.AuthService;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;

@Path("/")
public class AuthResource {

    @PermitAll
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(User user) {
        if (user == null) {
            throw new GenericException(GenericMessageMeta.NULL_OBJECT, User.class, Response.Status.BAD_REQUEST);
        }
        user.checkFieldOuterSettable();

        int userId = AuthService.register(user);

        return Response.created(URI.create("/users/" + userId)).build();
    }

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        if (user == null) {
            throw new GenericException(GenericMessageMeta.NULL_OBJECT, User.class, Response.Status.BAD_REQUEST);
        }
        user.checkFieldOuterSettable();

        Map<String, Object> result = AuthService.login(user);

        return Response.ok(result).build();
    }

    @POST
    @Path("/ping/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ping(@PathParam("userId") Integer userId) {
        if (userId == null) {
            throw new GenericException(GenericMessageMeta.NULL_ID, User.class, Response.Status.BAD_REQUEST);
        }

        if (!User.isValidUserId(userId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, User.class, Response.Status.BAD_REQUEST);
        }

        Map<String, Object> result = AuthService.ping(userId);

        return Response.ok(result).build();
    }
}
