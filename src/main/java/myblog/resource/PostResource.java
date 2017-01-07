package myblog.resource;

import myblog.dao.Pagination;
import myblog.domain.Post;
import myblog.dao.Sort;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.service.PostService;

import javax.annotation.security.PermitAll;
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
            throw new GenericException(GenericMessageMeta.NULL_OBJECT, Post.class, Response.Status.BAD_REQUEST);
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
            throw new GenericException(GenericMessageMeta.NULL_ID, Post.class, Response.Status.BAD_REQUEST);
        }
        if (!Post.isValidPostId(postId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, Post.class, Response.Status.BAD_REQUEST);
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
            throw new GenericException(GenericMessageMeta.NULL_ID, Post.class, Response.Status.BAD_REQUEST);
        }
        if (!Post.isValidPostId(postId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, Post.class, Response.Status.BAD_REQUEST);
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

    @PermitAll
    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPostById(@PathParam("postId") Integer postId,
                            @QueryParam("withCategory") Boolean withCategory,
							@QueryParam("withUser") Boolean withUser) {
        if (postId == null) {
            throw new GenericException(GenericMessageMeta.NULL_ID, Post.class, Response.Status.BAD_REQUEST);
        }
        if (!Post.isValidPostId(postId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, Post.class, Response.Status.BAD_REQUEST);
        }

        Post post = PostService.getPostById(postId,
				withCategory == null ? false : withCategory,
				withUser == null ? false : withUser);
        if (post != null) {
            return post;
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, Post.class, Response.Status.BAD_REQUEST);
        }
    }

	@PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> getPosts(@QueryParam("user_id") Integer userId,
                               @QueryParam("category_id") Integer categoryId,
                               @QueryParam("post_title") String postTitle,
                               @QueryParam("post_published") Boolean postPublished,
                               @QueryParam("post_enabled") Boolean postEnabled,
                               @QueryParam("withCategory") Boolean withCategory,
                               @QueryParam("withUser") Boolean withUser) {
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

        List<Post> posts = PostService.getPosts(post,
                withCategory == null ? false : withCategory,
                withUser == null ? false : withUser);
        if (posts != null && posts.size() > 0) {
            return posts;
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, Post.class, Response.Status.NOT_FOUND);
        }
    }

	@PermitAll
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
                                        @QueryParam("order_type") String orderType,
                                        @QueryParam("withCategory") Boolean withCategory,
                                        @QueryParam("withUser") Boolean withUser) {
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
        Sort<Post> sort = new Sort<Post>(orderBy, orderType, Post.class);
        page = PostService.getPostList(post, page, sort,
                withCategory == null ? false : withCategory,
                withUser == null ? false : withUser);

        if (page != null && page.getData().size() > 0) {
            return page;
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, Post.class, Response.Status.NOT_FOUND);
        }
    }
}