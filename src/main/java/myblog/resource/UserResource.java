package myblog.resource;

import myblog.domain.User;
import myblog.exception.FieldNotOuterSettableException;
import myblog.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(User user) {
        try {
            user.checkFieldOuterSettable();
        } catch (FieldNotOuterSettableException e) {
            throw new BadRequestException(e.getMessage(), e);
        }

        boolean hasIdentifier = user.getUser_telephone() != null
                || user.getUser_name() != null
                || user.getUser_email() != null;
        if (hasIdentifier && user.getUser_password() != null) {
            String jwt = UserService.userLogin(user);

            return Response.ok(jwt).build();
        } else {
            throw new BadRequestException("Unexpected user credential: Absence value");
        }
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userRegister(User user) {
        try {
            user.checkFieldOuterSettable();
        } catch (FieldNotOuterSettableException e) {
            throw new BadRequestException(e.getMessage(), e);
        }

        return Response.noContent().build();
    }
}
