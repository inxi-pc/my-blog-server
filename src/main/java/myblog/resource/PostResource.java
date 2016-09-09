package myblog.resource;

import myblog.Helper;
import myblog.model.SqlOrder;
import myblog.model.SqlPagination;
import myblog.model.Post;
import myblog.service.PostService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/posts")
public class PostResource {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public int createPost(@FormParam("user_id") int user_id,
                          @FormParam("post_title") String post_title,
                          @FormParam("post_content") String post_content,
                          @FormParam("post_published") boolean post_published) {
        if (user_id > 0) {
            Post insert = new Post();
            if (!Helper.isNullOrEmpty(post_title)) {
                insert.post_title = post_title;
            }
            if (!Helper.isNullOrEmpty(post_content)) {
                insert.post_content = post_content;
            }
            insert.user_id = user_id;
            insert.post_published = post_published;

            return PostService.createPost(insert);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean updatePost(@FormParam("post_id") int post_id,
                              @FormParam("user_id") int user_id,
                              @FormParam("post_title") String post_title,
                              @FormParam("post_content") String post_content,
                              @FormParam("post_published") boolean post_published) {
        if (post_id > 0) {
            Post update = new Post();
            if (user_id != 0) {
                update.user_id = user_id;
            }
            if (!Helper.isNullOrEmpty(post_title)) {
                update.post_title = post_title;
            }
            if (!Helper.isNullOrEmpty(post_content)) {
                update.post_content = post_content;
            }

            return PostService.updatePost(post_id, update);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPost(@PathParam("postId") int postId) {
        if (postId > 0) {
            return PostService.getPostById(postId);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> getPosts(@QueryParam("limit") int limit,
                               @QueryParam("offset") int offset,
                               @QueryParam("orderBy") String orderBy,
                               @QueryParam("orderType") String orderType) {
        SqlPagination page = new SqlPagination(limit, offset);
        SqlOrder order = new SqlOrder(orderBy, orderType);

        return PostService.getPosts(page, order);
    }

}