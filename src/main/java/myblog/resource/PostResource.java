package myblog.resource;

import myblog.domain.Pagination;
import myblog.domain.Post;
import myblog.domain.Sort;
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
        int postId = PostService.createPost(post);

        if (Post.isValidPostId(postId)) {
            return Response.created(URI.create("/posts/" + postId)).build();
        } else {
            throw new InternalServerErrorException("Unexpected error");
        }
    }

    @DELETE
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@PathParam("postId") Integer postId) {
        if (postId == null) {
            throw new BadRequestException("Unexpected post id: Absence value");
        }

        if (Post.isValidPostId(postId)) {
            if (PostService.deletePost(postId)) {
                return Response.noContent().build();
            } else {
                throw new InternalServerErrorException("Unexpected error");
            }
        } else {
            throw new BadRequestException("Unexpected post id: Invalid value");
        }
    }

    @PUT
    @Path("/{postId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePost(@PathParam("postId") Integer postId, Post post) {
        if (postId == null) {
            throw new BadRequestException("Unexpected post id: Absence value");
        }

        if (PostService.updatePost(postId, post)) {
            return Response.noContent().build();
        } else {
            throw new InternalServerErrorException("Unexpected error");
        }
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPostById(@PathParam("postId") Integer postId) {
        if (postId == null) {
            throw new BadRequestException("Unexpected post id: Absence value");
        }

        if (Post.isValidPostId(postId)) {
            Post post = PostService.getPostById(postId);
            if (post != null) {
                return post;
            } else {
                throw new NotFoundException("Not found post: Id = " + postId);
            }
        } else {
            throw new BadRequestException("Unexpected post id: Not valid value");
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
        if (userId != null) {
            post.setUser_id(userId);
        }
        if (categoryId != null) {
            post.setCategory_id(categoryId);
        }
        if (postTitle != null) {
            post.setPost_title(postTitle);
        }
        if (postPublished != null) {
            post.setPost_published(postPublished);
        }
        if (postEnabled != null) {
            post.setPost_enabled(postEnabled);
        }

        List<Post> posts = PostService.getPosts(post);
        if (posts != null) {
            if (posts.size() > 0) {
                return posts;
            } else {
                throw new NotFoundException("Not found posts");
            }
        } else {
            throw new InternalServerErrorException("Unexpected error");
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
                                @QueryParam("limit") Integer limit,
                                @QueryParam("offset") Integer offset,
                                @QueryParam("order_by") String orderBy,
                                @QueryParam("order_type") String orderType) {
        Post post = new Post();
        if (userId != null) {
            post.setUser_id(userId);
        }
        if (categoryId != null) {
            post.setCategory_id(categoryId);
        }
        if (postTitle != null) {
            post.setPost_title(postTitle);
        }
        if (postPublished != null) {
            post.setPost_published(postPublished);
        }
        if (postEnabled != null) {
            post.setPost_enabled(postEnabled);
        }

        Pagination<Post> page = new Pagination<Post>(limit, offset);
        Sort<Post> order = new Sort<Post>(orderBy, orderType, Post.class);
        page = PostService.getPostList(post, page, order);

        if (page != null) {
            if (page.getData().size() > 0) {
                return Response.ok(page).build();
            } else {
                throw new NotFoundException("Not found posts");
            }
        } else {
            throw new InternalServerErrorException("Unexpected error");
        }
    }
}