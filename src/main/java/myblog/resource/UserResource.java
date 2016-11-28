package myblog.resource;

import myblog.domain.User;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User insert) {
        if (insert == null) {
            throw new GenericException(GenericMessageMeta.NULL_OBJECT, User.class, Response.Status.BAD_REQUEST);
        }
        insert.checkFieldOuterSettable();

        int userId = UserService.createUser(insert);

        return Response.ok(userId).build();
    }

    @DELETE
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(Integer userId) {
        if (userId == null) {
            throw new GenericException(GenericMessageMeta.NULL_ID, User.class, Response.Status.BAD_REQUEST);
        }
        if (!User.isValidUserId(userId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, User.class, Response.Status.BAD_REQUEST);
        }

        if (UserService.deleteUser(userId)) {
            return Response.noContent().build();
        } else {
            throw new InternalServerErrorException();
        }
    }

    @PUT
    @Path("/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("userId") Integer userId, User update) {
        if (userId == null) {
            throw new GenericException(GenericMessageMeta.NULL_ID, User.class, Response.Status.BAD_REQUEST);
        }
        if (!User.isValidUserId(userId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, User.class, Response.Status.BAD_REQUEST);
        }
        if (update == null) {
            return Response.noContent().build();
        }
        update.checkFieldOuterSettable();

        if (UserService.updateUser(userId, update)) {
            return Response.noContent().build();
        } else {
            throw new InternalServerErrorException();
        }
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
