package myblog.resource;

import myblog.domain.User;
import myblog.exception.GenericException;
import myblog.exception.LiteralMessageMeta;
import myblog.service.UserService;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
}
