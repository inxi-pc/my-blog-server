package myblog.resource;

import myblog.domain.Pagination;
import myblog.domain.Post;
import myblog.domain.Sort;
import myblog.exception.FieldNotInsertableException;
import myblog.exception.FieldNotNullableException;
import myblog.exception.FieldNotOuterSettableException;
import myblog.exception.FieldNotUpdatableException;
import myblog.service.PostService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;

@Path("/posts")
public class PostResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(Post insert) {
        try {
            insert.checkFieldOuterSettable();
        } catch (FieldNotOuterSettableException e) {
            throw new BadRequestException(e);
        }

        int postId = PostService.createPost(insert);

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
    public Response updatePost(@PathParam("postId") Integer postId, Post update) {
        if (postId == null) {
            throw new BadRequestException("Unexpected post id: Absence value");
        }

        try {
            update.checkFieldOuterSettable();
        } catch (FieldNotOuterSettableException e) {
            throw new BadRequestException(e);
        }

        if (PostService.updatePost(postId, update)) {
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
                               @QueryParam("post_published") Boolean postPublished,
                               @QueryParam("post_enabled") Boolean postEnabled) {
        Post post = new Post();
        boolean noQueryParam = true;
        if (userId != null) {
            if (Post.isValidUserId(userId)) {
                post.setUser_id(userId);
                noQueryParam = false;
            } else {
                throw new BadRequestException("Unexpected user id: Not valid value");
            }
        }
        if (categoryId != null) {
            if (Post.isValidCategoryId(categoryId)) {
                post.setCategory_id(categoryId);
                noQueryParam = false;
            } else {
                throw new BadRequestException("Unexpected category id: Not valid value");
            }
        }
        if (postTitle != null) {
            if (Post.isValidPostTitle(postTitle)) {
                post.setPost_title(postTitle);
                noQueryParam = false;
            } else {
                throw new BadRequestException("Unexpected post title: Not valid value");
            }
        }
        if (postPublished != null) {
            if (Post.isValidPostPublished(postPublished)) {
                post.setPost_published(postPublished);
                noQueryParam = false;
            } else {
                throw new BadRequestException("Unexpected post published: Not valid value");
            }
        }
        if (postEnabled != null) {
            if (Post.isValidPostEnabled(postEnabled)) {
                post.setPost_enabled(postEnabled);
                noQueryParam = false;
            } else {
                throw new BadRequestException("Unexpected post enabled: Not valid value");
            }
        }
        if (noQueryParam) {
            throw new BadRequestException("Unexpected post query parameter: No parameters");
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
                throw new BadRequestException("Unexpected user id: Not valid value");
            }
        }
        if (categoryId != null) {
            if (Post.isValidCategoryId(categoryId)) {
                post.setCategory_id(categoryId);
            } else {
                throw new BadRequestException("Unexpected category id: Not valid value");
            }
        }
        if (postTitle != null) {
            if (Post.isValidPostTitle(postTitle)) {
                post.setPost_title(postTitle);
            } else {
                throw new BadRequestException("Unexpected post title: Not valid value");
            }
        }
        if (postPublished != null) {
            if (Post.isValidPostPublished(postPublished)) {
                post.setPost_published(postPublished);
            } else {
                throw new BadRequestException("Unexpected post published: Not valid value");
            }
        }
        if (postEnabled != null) {
            if (Post.isValidPostEnabled(postEnabled)) {
                post.setPost_enabled(postEnabled);
            } else {
                throw new BadRequestException("Unexpected post enabled: Not valid value");
            }
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