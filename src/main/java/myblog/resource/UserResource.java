package myblog.resource;

import myblog.dao.Pagination;
import myblog.dao.Sort;
import myblog.domain.User;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(@QueryParam("user_enabled") Boolean userEnabled) {
        User user = new User();
        if (userEnabled != null) {
            user.setUser_enabled(userEnabled);
        }

        List<User> users = UserService.getUsers(user);
        if (users != null && users.size() > 0) {
            return users;
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, User.class, Response.Status.NOT_FOUND);
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Pagination<User> getUserList(@QueryParam("user_enabled") Boolean userEnabled,
                                        @QueryParam("limit") Integer limit,
                                        @QueryParam("offset") Integer offset,
                                        @QueryParam("order_by") String orderBy,
                                        @QueryParam("order_type") String orderType) {
        User user = new User();
        if (userEnabled != null) {
            user.setUser_enabled(userEnabled);
        }

        Pagination<User> page = new Pagination<User>(limit, offset);
        Sort<User> sort = new Sort<User>(orderBy, orderType, User.class);
        page = UserService.getUserList(user, page, sort);

        if (page != null && page.getData().size() > 0) {
            return page;
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, User.class, Response.Status.NOT_FOUND);
        }
    }
}
