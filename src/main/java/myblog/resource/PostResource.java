package myblog.resource;

import myblog.Helper;
import myblog.model.business.OrderBo;
import myblog.model.business.PaginationBo;
import myblog.model.business.PostBo;
import myblog.model.persistence.Post;
import myblog.service.PostService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;

@Path("/posts")
public class PostResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int createPost(PostBo postBo) {
        if (postBo.category_id == null || postBo.user_id == null) {
            throw new WebApplicationException("category_id is required",
                    Response.Status.BAD_REQUEST);
        }
        postBo.setDefaultValue();

        return PostService.createPost(postBo);
    }

    @PUT
    @Path("/{postId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean updatePost(@PathParam("postId") Integer postId,
                              PostBo postBo) {
        if (postId != null) {
            return PostService.updatePost(postId, postBo);
        } else {
            throw new WebApplicationException("post_id is required",
                    Response.Status.BAD_REQUEST);
        }
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
    public List<Post> getPostList(@QueryParam("limit") int limit,
                                  @QueryParam("offset") int offset,
                                  @QueryParam("order_by") String orderBy,
                                  @QueryParam("order_type") String orderType) {
        PaginationBo page = new PaginationBo(limit, offset);
        OrderBo order = new OrderBo(orderBy, orderType);

        return PostService.getPostList(page, order);
    }

}