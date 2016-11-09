package myblog.resource;

import myblog.domain.Pagination;
import myblog.domain.Post;
import myblog.domain.Sort;
import myblog.exception.DomainException;
import myblog.exception.HttpExceptionFactory;
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
    public Response createPost(Post insert) {
        if (insert == null) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    Post.class,
                    HttpExceptionFactory.Reason.ABSENCE_VALUE);
        }

        try {
            insert.checkFieldOuterSettable();
        } catch (DomainException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }

        int postId = PostService.createPost(insert);
        if (Post.isValidPostId(postId)) {
            return Response.created(URI.create("/posts/" + postId)).build();
        } else {
            throw HttpExceptionFactory.produce(
                    InternalServerErrorException.class,
                    HttpExceptionFactory.Type.CREATE_FAILED,
                    Post.class,
                    HttpExceptionFactory.Reason.INVALID_PRIMARY_KEY_VALUE);
        }
    }

    @DELETE
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@PathParam("postId") Integer postId) {
        if (postId == null) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "postId",
                    HttpExceptionFactory.Reason.ABSENCE_VALUE);
        }

        if (Post.isValidPostId(postId)) {
            if (PostService.deletePost(postId)) {
                return Response.noContent().build();
            } else {
                throw HttpExceptionFactory.produce(
                        InternalServerErrorException.class,
                        HttpExceptionFactory.Type.DELETE_FAILED,
                        Post.class,
                        HttpExceptionFactory.Reason.UNDEFINED_ERROR);
            }
        } else {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "postId",
                    HttpExceptionFactory.Reason.INVALID_VALUE);
        }
    }

    @PUT
    @Path("/{postId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePost(@PathParam("postId") Integer postId, Post update) {
        if (postId == null) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "postId",
                    HttpExceptionFactory.Reason.ABSENCE_VALUE);
        }

        if (!Post.isValidPostId(postId)) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "postId",
                    HttpExceptionFactory.Reason.INVALID_VALUE);
        }

        if (update == null) {
            return Response.noContent().build();
        }

        try {
            update.checkFieldOuterSettable();
        } catch (DomainException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }

        if (PostService.updatePost(postId, update)) {
            return Response.noContent().build();
        } else {
            throw HttpExceptionFactory.produce(
                    InternalServerErrorException.class,
                    HttpExceptionFactory.Type.UPDATE_FAILED,
                    Post.class,
                    HttpExceptionFactory.Reason.UNDEFINED_ERROR);
        }
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPostById(@PathParam("postId") Integer postId) {
        if (postId == null) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "postId",
                    HttpExceptionFactory.Reason.ABSENCE_VALUE);
        }

        if (!Post.isValidPostId(postId)) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "postId",
                    HttpExceptionFactory.Reason.INVALID_VALUE);
        }

        Post post = PostService.getPostById(postId);
        if (post != null) {
            return post;
        } else {
            throw HttpExceptionFactory.produce(
                    NotFoundException.class,
                    HttpExceptionFactory.Type.NOT_FOUND,
                    Post.class,
                    HttpExceptionFactory.Reason.NOT_EXIST);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> getPosts(@QueryParam("user_id") Integer userId,
                               @QueryParam("category_id") Integer categoryId,
                               @QueryParam("post_title") String postTitle,
                               @QueryParam("post_published") Boolean postPublished,
                               @QueryParam("post_enabled") Boolean postEnabled) {
        Post post = new Post();
        if (userId != null) {
            if (Post.isValidUserId(userId)) {
                post.setUser_id(userId);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "user_id",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (categoryId != null) {
            if (Post.isValidCategoryId(categoryId)) {
                post.setCategory_id(categoryId);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_id",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (postTitle != null) {
            if (Post.isValidPostTitle(postTitle)) {
                post.setPost_title(postTitle);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "post_title",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (postPublished != null) {
            if (Post.isValidPostPublished(postPublished)) {
                post.setPost_published(postPublished);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "post_published",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (postEnabled != null) {
            if (Post.isValidPostEnabled(postEnabled)) {
                post.setPost_enabled(postEnabled);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "post_enabled",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }

        List<Post> posts = PostService.getPosts(post);
        if (posts != null && posts.size() > 0) {
            return posts;
        } else {
            throw HttpExceptionFactory.produce(
                    NotFoundException.class,
                    HttpExceptionFactory.Type.NOT_FOUND,
                    Post.class,
                    HttpExceptionFactory.Reason.NOT_ELIGIBLE);
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Pagination<Post> getPostList(@QueryParam("user_id") Integer userId,
                                @QueryParam("category_id") Integer categoryId,
                                @QueryParam("post_title") String postTitle,
                                @QueryParam("post_published") Boolean postPublished,
                                @QueryParam("post_enabled") Boolean postEnabled,
                                @QueryParam("limit") Integer limit,
                                @QueryParam("offset") Integer offset,
                                @QueryParam("order_by") String orderBy,
                                @QueryParam("order_type") String orderType) {
        Post post = new Post();
        if (userId != null) {
            if (Post.isValidUserId(userId)) {
                post.setUser_id(userId);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "user_id",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (categoryId != null) {
            if (Post.isValidCategoryId(categoryId)) {
                post.setCategory_id(categoryId);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_id",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (postTitle != null) {
            if (Post.isValidPostTitle(postTitle)) {
                post.setPost_title(postTitle);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "post_title",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (postPublished != null) {
            if (Post.isValidPostPublished(postPublished)) {
                post.setPost_published(postPublished);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "post_published",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (postEnabled != null) {
            if (Post.isValidPostEnabled(postEnabled)) {
                post.setPost_enabled(postEnabled);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "post_enabled",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }

        Pagination<Post> page = new Pagination<Post>(limit, offset);
        Sort<Post> sort = new Sort<Post>(orderBy, orderType, Post.class);
        page = PostService.getPostList(post, page, sort);

        if (page != null && page.getData().size() > 0) {
            return page;
        } else {
            throw HttpExceptionFactory.produce(
                    NotFoundException.class,
                    HttpExceptionFactory.Type.NOT_FOUND,
                    Post.class,
                    HttpExceptionFactory.Reason.NOT_ELIGIBLE);
        }
    }
}