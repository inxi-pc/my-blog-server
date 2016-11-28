package myblog.resource;


import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import myblog.App;
import myblog.domain.User;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.exception.LiteralMessageMeta;
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
    public Response registerUser(User user) {
        if (user == null) {
            throw new GenericException(GenericMessageMeta.NULL_OBJECT, User.class, Response.Status.BAD_REQUEST);
        }
        user.checkFieldOuterSettable();

        int userId = AuthService.registerUser(user);

        return Response.created(URI.create("/users/" + userId)).build();
    }

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(User user) {
        if (user == null) {
            throw new GenericException(GenericMessageMeta.NULL_OBJECT, User.class, Response.Status.BAD_REQUEST);
        }
        user.checkFieldOuterSettable();

        Map<String, Object> result = AuthService.loginUser(user);

        return Response.ok(result).build();
    }

    @PermitAll
    @POST
    @Path("/ping/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response refreshToken(@PathParam("userId") Integer userId) {
        if (userId == null) {
            throw new GenericException(GenericMessageMeta.NULL_OBJECT, User.class, Response.Status.BAD_REQUEST);
        }

        return Response.noContent().build();
    }
}
