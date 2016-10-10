package myblog.resource;

import myblog.domain.Order;
import myblog.domain.Pagination;
import myblog.domain.Post;
import myblog.service.PostService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/posts")
public class PostResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public int createPost(Post post) {
        if (Post.isValidUserId(post.getUser_id())
                && Post.isValidCategoryId(post.getCategory_id())) {
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
        } else {
            throw new BadRequestException();
        }
    }

    @PUT
    @Path("/{postId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean updatePost(@PathParam("postId") Integer postId, Post post) {
        if (postId != null && !post.checkAllFieldsIsNullExceptPK()) {
            return PostService.updatePost(post);
        } else {
            throw new BadRequestException();
        }
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPostById(@PathParam("postId") Integer postId) {
        if (Post.isValidPostId(postId)) {
            return PostService.getPostById(postId);
        } else {
            throw new BadRequestException();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> getPosts(@QueryParam("user_id") Integer userId,
                               @QueryParam("category_id") Integer categoryId,
                               @QueryParam("post_title") String postTitle,
                               @QueryParam("duration_begin") String durationBegin,
                               @QueryParam("duration_end") String durationEnd,
                               @QueryParam("post_published") Boolean postPublished,
                               @QueryParam("post_enabled") Boolean postEnabled) {
        Post post = new Post();
        post.setPost_title(postTitle);
        if (Post.isValidUserId(userId)) {
            post.setUser_id(userId);
        }
        if (Post.isValidCategoryId(categoryId)) {
            post.setCategory_id(categoryId);
        }
        if (Post.isValidDurationBegin(durationBegin)) {
            post.setUser_id(userId);
        }
        if (Post.isValidDurationEnd(durationEnd)) {
            post.setDuration_end(durationEnd);
        }
        if (Post.isValidPostPublished(postPublished)) {
            post.setPost_published(postPublished);
        }
        if (Post.isValidPostEnabled(postEnabled)) {
            post.setPost_enabled(postEnabled);
        }

        return PostService.getPosts(post);
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Pagination getPostList(@QueryParam("user_id") Integer userId,
                                  @QueryParam("category_id") Integer categoryId,
                                  @QueryParam("post_title") String postTitle,
                                  @QueryParam("duration_begin") String durationBegin,
                                  @QueryParam("duration_end") String durationEnd,
                                  @QueryParam("post_published") Boolean postPublished,
                                  @QueryParam("post_enabled") Boolean postEnabled,
                                  @QueryParam("limit") int limit,
                                  @QueryParam("offset") int offset,
                                  @QueryParam("order_by") String orderBy,
                                  @QueryParam("order_type") String orderType) {

        Post post = new Post();
        post.setPost_title(postTitle);
        if (Post.isValidUserId(userId)) {
            post.setUser_id(userId);
        }
        if (Post.isValidCategoryId(categoryId)) {
            post.setCategory_id(categoryId);
        }
        if (Post.isValidDurationBegin(durationBegin)) {
            post.setUser_id(userId);
        }
        if (Post.isValidDurationEnd(durationEnd)) {
            post.setDuration_end(durationEnd);
        }
        if (Post.isValidPostPublished(postPublished)) {
            post.setPost_published(postPublished);
        }
        if (Post.isValidPostEnabled(postEnabled)) {
            post.setPost_enabled(postEnabled);
        }
        Pagination<Post> page = new Pagination<Post>(limit, offset);
        Order<Post> order = new Order<Post>(orderBy, orderType, Post.class);

        return PostService.getPostList(post, page, order);
    }

}