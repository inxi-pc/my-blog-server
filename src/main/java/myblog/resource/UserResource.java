package myblog.resource;

import myblog.domain.User;
import myblog.exception.DomainException;
import myblog.service.UserService;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/users")
public class UserResource {

    @PermitAll
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userRegister(User user) {
        if (user == null) {
            throw new BadRequestException("Unexpected user: Absence value");
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
            throw new InternalServerErrorException("Unexpected error");
        }
    }

    @PermitAll
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(User user) {
        if (user == null) {
            throw new BadRequestException("Unexpected user: Absence value");
        }

        try {
            user.checkFieldOuterSettable();
        } catch (DomainException e) {
            throw new BadRequestException(e.getMessage(), e);
        }

        String token = UserService.loginUser(user);
        if (token == null) {
            throw new NotFoundException("Not found user: Invalid identifier or password");
        } else {
            return Response.ok(token).build();
        }
    }
}
