package myblog.resource;

import myblog.model.persistence.Order;
import myblog.model.persistence.Pagination;
import myblog.model.persistence.Post;
import myblog.service.PostService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/posts")
public class PostResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int createPost(Post post) {
        if (post.getCategory_id() == null || post.getUser_id() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } else {
            if (post.getPost_enabled() == null) {
                post.setPost_enabled(null);
            }
            if (post.getPost_published() == null) {
                post.setPost_published(null);
            }
            if (post.getPost_created_at() == null) {
                post.setPost_created_at(null);
            }
            if (post.getPost_updated_at() == null) {
                post.setPost_updated_at(null);
            }

            return PostService.createPost(post);
        }
    }

    @PUT
    @Path("/{postId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean updatePost(Post post) {
        if (post.getPost_id() == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        } else {
            return PostService.updatePost(post.getPost_id(), post);
        }
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPostById(@PathParam("postId") Integer postId) {
        if (postId != null) {
            return PostService.getPostById(postId);
        } else {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Pagination getPostList(@QueryParam("limit") int limit,
                                  @QueryParam("offset") int offset,
                                  @QueryParam("order_by") String orderBy,
                                  @QueryParam("order_type") String orderType) {
        Pagination<Post> page = new Pagination<Post>(limit, offset);
        Order<Post> order = new Order<Post>(orderBy, orderType, Post.class);

        return PostService.getPostList(page, order);
    }

}