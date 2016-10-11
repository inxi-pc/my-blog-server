package myblog.resource;

import myblog.domain.Order;
import myblog.domain.Pagination;
import myblog.domain.Post;
import myblog.service.PostService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/posts")
public class PostResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(Post post) {
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
            int postId = PostService.createPost(post);

            if (Post.isValidPostId(postId)) {
                return Response.created(URI.create("/posts/" + postId)).build();
            } else {
                throw new InternalServerErrorException();
            }
        } else {
            throw new BadRequestException();
        }
    }

    @PUT
    @Path("/{postId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePost(@PathParam("postId") Integer postId, Post post) {
        if (Post.isValidPostId(postId) && !post.checkAllFieldsIsNullExceptPK()) {
            if (PostService.getPostById(postId) != null) {
                if (PostService.updatePost(post)) {
                    return Response.noContent().build();
                } else {
                    throw new InternalServerErrorException();
                }
            } else {
                throw new NotFoundException();
            }
        } else {
            throw new BadRequestException();
        }
    }

    @DELETE
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@PathParam("postId") Integer postId) {
        if (Post.isValidPostId(postId)) {
            if (PostService.getPostById(postId) != null) {
                if (PostService.deletePost(postId)) {
                    return Response.noContent().build();
                } else {
                    throw new InternalServerErrorException();
                }
            } else {
                throw new NotFoundException();
            }
        } else {
            throw new BadRequestException();
        }
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPostById(@PathParam("postId") Integer postId) {
        if (Post.isValidPostId(postId)) {
            Post post = PostService.getPostById(postId);
            if (post != null) {
                return post;
            } else {
                throw new NotFoundException();
            }
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
        if (Post.isValidUserId(userId)) {
            post.setUser_id(userId);
        }
        if (Post.isValidCategoryId(categoryId)) {
            post.setCategory_id(categoryId);
        }
        if (Post.isValidPostTitle(postTitle)) {
            post.setPost_title(postTitle);
        }
        if (Post.isValidDurationBegin(durationBegin)) {
            post.setDuration_begin(durationBegin);
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

        if (!post.checkAllFieldsIsNull()) {
            List<Post> posts = PostService.getPosts(post);
            if (posts != null) {
                if (posts.size() > 0) {
                    return posts;
                } else {
                    throw new NotFoundException();
                }
            } else {
                throw new InternalServerErrorException();
            }
        } else {
            throw new BadRequestException();
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPostList(@QueryParam("user_id") Integer userId,
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
        if (Post.isValidUserId(userId)) {
            post.setUser_id(userId);
        }
        if (Post.isValidCategoryId(categoryId)) {
            post.setCategory_id(categoryId);
        }
        if (Post.isValidPostTitle(postTitle)) {
            post.setPost_title(postTitle);
        }
        if (Post.isValidDurationBegin(durationBegin)) {
            post.setDuration_begin(durationBegin);
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
        page = PostService.getPostList(post, page, order);

        if (page != null) {
            if (page.getData().size() > 0) {
                return Response.ok(page).build();
            } else {
                throw new NotFoundException();
            }
        } else {
            throw new InternalServerErrorException();
        }
    }
}