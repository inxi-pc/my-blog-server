package myblog.resource;

import myblog.domain.User;
import myblog.exception.DomainException;
import myblog.exception.HttpExceptionFactory;
import myblog.service.UserService;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
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
    public Response userRegister(User user) {
        if (user == null) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    User.class,
                    HttpExceptionFactory.Reason.ABSENCE_VALUE);
        }

        try {
            user.checkFieldOuterSettable();
        } catch (DomainException e) {
            throw new BadRequestException(e.getMessage(), e);
        }

        int userId = UserService.registerUser(user);

        if (User.isValidUserId(userId)) {
            return Response.created(URI.create("/users/" + userId)).build();
        } else {
            throw HttpExceptionFactory.produce(
                    InternalServerErrorException.class,
                    HttpExceptionFactory.Type.CREATE_FAILED,
                    User.class,
                    HttpExceptionFactory.Reason.INVALID_PRIMARY_KEY_VALUE);
        }
    }

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(User user) {
        if (user == null) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    User.class,
                    HttpExceptionFactory.Reason.ABSENCE_VALUE);
        }

        try {
            user.checkFieldOuterSettable();
        } catch (DomainException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }

        Map<String, Object> result = UserService.loginUser(user);
        if (result == null) {
            throw HttpExceptionFactory.produce(
                    InternalServerErrorException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    HttpExceptionFactory.Reason.UNDEFINED_ERROR);
        } else {
            Cookie cookie = new Cookie("token", (String) result.get("token"));
            return Response.ok(result.get("user"))
                    .cookie(new NewCookie(cookie, null, 60, true))
                    .build();
        }
    }
}
