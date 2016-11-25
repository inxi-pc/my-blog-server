package myblog.resource;

import myblog.domain.User;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.exception.LiteralMessageMeta;
import myblog.service.UserService;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Map;

@Path("/users")
public class UserResource {

    @PermitAll
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUser(User user) {
        if (user == null) {
            throw new GenericException(LiteralMessageMeta.NULL_USER_TO_REGISTER, Response.Status.BAD_REQUEST);
        }
        user.checkFieldOuterSettable();

        int userId = UserService.registerUser(user);

        return Response.created(URI.create("/users/" + userId)).build();
    }

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(User user) {
        if (user == null) {
            throw new GenericException(LiteralMessageMeta.NULL_USER_TO_LOGIN, Response.Status.BAD_REQUEST);
        }
        user.checkFieldOuterSettable();

        Map<String, Object> result = UserService.loginUser(user);

        return Response.ok(result).build();
    }

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("userId") Integer userId) {
        User user = UserService.getUserById(userId);

        if (user != null) {
            return Response.ok(user).build();
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, User.class, Response.Status.NOT_FOUND);
        }
    }
}
