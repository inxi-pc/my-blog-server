package myblog.resource;

import myblog.model.Post;
import myblog.model.SqlOrder;
import myblog.model.SqlPagination;
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
    public int createPost(@FormParam("userId") Integer userId,
                          @FormParam("categoryId") Integer categoryId,
                          @FormParam("postTitle") String postTitle,
                          @FormParam("postContent") String postContent,
                          @FormParam("postPublished") Boolean postPublished,
                          @FormParam("postEnabled") Boolean postEnabled) {
        try {
            Post insert = new Post();
            insert.setUser_id(userId);
            insert.setCategory_id(categoryId);
            insert.setPost_title(postTitle);
            insert.setPost_content(postContent);
            insert.setPost_published(postPublished);
            insert.setPost_enabled(postEnabled);
            insert.setPost_created_at(null);
            insert.setPost_updated_at(null);

            return PostService.createPost(insert);
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    @Path("/{postId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean updatePost(@PathParam("postId") Integer postId,
                              @FormParam("user_id") Integer userId,
                              @FormParam("category_id") Integer categoryId,
                              @FormParam("post_title") Integer postTitle,
                              @FormParam("post_content") String postContent,
                              @FormParam("post_published") Boolean postPublished,
                              @FormParam("post_enabled") Boolean postEnabled) {
        Post update = new Post();
        try {
            update.setPost_id(postId);
            if (userId != null) {
                update.setUser_id(userId);
            }
            if (categoryId != null) {
                update.setCategory_id(categoryId);
            }
//            if (postTitle != null) {
//                update.setPost_title(postTitle);
//            }
            if (postContent != null) {
                update.setPost_content(postContent);
            }
            if (postPublished != null) {
                update.setPost_published(postPublished);
            }
            if (postEnabled != null) {
                update.setPost_enabled(postEnabled);
            }
            update.setPost_updated_at(null);
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return PostService.updatePost(postId, update);
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPost(@PathParam("postId") Integer postId) {
        if (postId != null) {
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