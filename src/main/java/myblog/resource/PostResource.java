package myblog.resource;

import myblog.domain.Pagination;
import myblog.domain.Post;
import myblog.domain.Sort;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
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
            throw new GenericException(
                    GenericMessageMeta.NULL_INSERTED_OBJECT,
                    Post.class,
                    Response.Status.BAD_REQUEST);
        }
        insert.checkFieldOuterSettable();

        int postId = PostService.createPost(insert);

        return Response.created(URI.create("/posts/" + postId)).build();
    }

    @DELETE
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@PathParam("postId") Integer postId) {
        if (postId == null) {
            throw new GenericException(
                    GenericMessageMeta.NULL_DELETED_ID,
                    Post.class,
                    Response.Status.BAD_REQUEST);
        }
        if (!Post.isValidPostId(postId)) {
            throw new GenericException(
                    GenericMessageMeta.INVALID_DELETED_ID,
                    Post.class,
                    Response.Status.BAD_REQUEST);
        }

        if (PostService.deletePost(postId)) {
            return Response.noContent().build();
        } else {
            throw new InternalServerErrorException();
        }
    }

    @PUT
    @Path("/{postId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePost(@PathParam("postId") Integer postId, Post update) {
        if (postId == null) {
            throw new GenericException(
                    GenericMessageMeta.NULL_UPDATED_ID,
                    Post.class,
                    Response.Status.BAD_REQUEST);
        }
        if (!Post.isValidPostId(postId)) {
            throw new GenericException(
                    GenericMessageMeta.INVALID_UPDATED_ID,
                    Post.class,
                    Response.Status.BAD_REQUEST);
        }
        if (update == null) {
            return Response.noContent().build();
        }
        update.checkFieldOuterSettable();

        if (PostService.updatePost(postId, update)) {
            return Response.noContent().build();
        } else {
            throw new InternalServerErrorException();
        }
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPostById(@PathParam("postId") Integer postId) {
        if (postId == null) {
            throw new GenericException(
                    GenericMessageMeta.NULL_QUERY_ID,
                    Post.class,
                    Response.Status.BAD_REQUEST);
        }
        if (!Post.isValidPostId(postId)) {
            throw new GenericException(
                    GenericMessageMeta.INVALID_UPDATED_ID,
                    Post.class,
                    Response.Status.BAD_REQUEST);
        }

        Post post = PostService.getPostById(postId);
        if (post != null) {
            return post;
        } else {
            throw new GenericException(
                    GenericMessageMeta.NOT_FOUND_OBJECT,
                    Post.class,
                    Response.Status.BAD_REQUEST);
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
                throw new GenericException(
                        GenericMessageMeta.INVALID_QUERY_PARAM,
                        "user_id",
                        Response.Status.BAD_REQUEST);
            }
        }
        if (categoryId != null) {
            if (Post.isValidCategoryId(categoryId)) {
                post.setCategory_id(categoryId);
            } else {
                throw new GenericException(
                        GenericMessageMeta.INVALID_QUERY_PARAM,
                        "category_id",
                        Response.Status.BAD_REQUEST);
            }
        }
        if (postTitle != null) {
            if (Post.isValidPostTitle(postTitle)) {
                post.setPost_title(postTitle);
            } else {
                throw new GenericException(
                        GenericMessageMeta.INVALID_QUERY_PARAM,
                        "post_title",
                        Response.Status.BAD_REQUEST);
            }
        }
        if (postPublished != null) {
            post.setPost_published(postPublished);
        }
        if (postEnabled != null) {
            post.setPost_enabled(postEnabled);
        }

        List<Post> posts = PostService.getPosts(post);
        if (posts != null && posts.size() > 0) {
            return posts;
        } else {
            throw new GenericException(
                    GenericMessageMeta.NOT_FOUND_OBJECT,
                    Post.class,
                    Response.Status.NOT_FOUND);
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
                throw new GenericException(
                        GenericMessageMeta.INVALID_QUERY_PARAM,
                        "user_id",
                        Response.Status.BAD_REQUEST);
            }
        }
        if (categoryId != null) {
            if (Post.isValidCategoryId(categoryId)) {
                post.setCategory_id(categoryId);
            } else {
                throw new GenericException(
                        GenericMessageMeta.INVALID_QUERY_PARAM,
                        "category_id",
                        Response.Status.BAD_REQUEST);
            }
        }
        if (postTitle != null) {
            if (Post.isValidPostTitle(postTitle)) {
                post.setPost_title(postTitle);
            } else {
                throw new GenericException(
                        GenericMessageMeta.INVALID_QUERY_PARAM,
                        "post_title",
                        Response.Status.BAD_REQUEST);
            }
        }
        if (postPublished != null) {
            post.setPost_published(postPublished);
        }
        if (postEnabled != null) {
            post.setPost_enabled(postEnabled);
        }

        Pagination<Post> page = new Pagination<Post>(limit, offset);
        Sort<Post> sort = new Sort<Post>(orderBy, orderType, Post.class);
        page = PostService.getPostList(post, page, sort);

        if (page != null && page.getData().size() > 0) {
            return page;
        } else {
            throw new GenericException(
                    GenericMessageMeta.NOT_FOUND_OBJECT,
                    Post.class,
                    Response.Status.NOT_FOUND);
        }
    }
}