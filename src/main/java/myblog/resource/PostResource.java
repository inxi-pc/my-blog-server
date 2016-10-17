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
        if (!Post.isValidCategoryId(post.getCategory_id())) {
            throw new BadRequestException("Unexpected category id");
        }
        if (!Post.isValidUserId(post.getUser_id())) {
            throw new BadRequestException("Unexpected user id");
        }
        if (!Post.isValidPostTitle(post.getPost_title())) {
            throw new BadRequestException("Unexpected post title");
        }
        if (!Post.isValidPostContent(post.getPost_content())) {
            throw new BadRequestException("Unexpected post content");
        }
        if (!Post.isValidPostPublished(post.getPost_published())) {
            throw new BadRequestException("Unexpected post published");
        }
        post.setPost_enabled(true);
        post.setPost_created_at(null);
        post.setPost_updated_at(null);

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
        if (postId != null && Post.isValidPostId(postId)) {
            if (PostService.getPostById(postId) != null) {
                if (PostService.deletePost(postId)) {
                    return Response.noContent().build();
                } else {
                    throw new InternalServerErrorException("Unexpected error");
                }
            } else {
                throw new NotFoundException("Not found post");
            }
        } else {
            throw new BadRequestException("Unexpected post id");
        }
    }

    @PUT
    @Path("/{postId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePost(@PathParam("postId") Integer postId, Post post) {
        if (postId == null || !Post.isValidPostId(postId)) {
            throw new BadRequestException("Unexpected post id");
        }
        if (post.getUser_id() != null) {
            if (!Post.isValidUserId(post.getUser_id())) {
                throw new BadRequestException("Unexpected user id");
            }
        }
        if (post.getCategory_id() != null) {
            if (!Post.isValidCategoryId(post.getCategory_id())) {
                throw new BadRequestException("Unexpected category id");
            }
        }
        if (post.getPost_title() != null) {
            if (!Post.isValidPostTitle(post.getPost_title())) {
                throw new BadRequestException("Unexpected post title");
            }
        }
        if (post.getPost_content() != null) {
            if (!Post.isValidPostContent(post.getPost_content())) {
                throw new BadRequestException("Unexpected post content");
            }
        }
        if (post.getPost_published() != null) {
            if (!Post.isValidPostPublished(post.getPost_published())) {
                throw new BadRequestException("Unexpected post published");
            }
        }
        post.setPost_updated_at(null);

        if (PostService.getPostById(postId) != null) {
            post.setPost_id(postId);
            if (PostService.updatePost(post)) {
                return Response.noContent().build();
            } else {
                throw new InternalServerErrorException("Unexpected error");
            }
        } else {
            throw new NotFoundException("Not found post");
        }
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPostById(@PathParam("postId") Integer postId) {
        if (postId != null && Post.isValidPostId(postId)) {
            Post post = PostService.getPostById(postId);
            if (post != null) {
                return post;
            } else {
                throw new NotFoundException("Not found post");
            }
        } else {
            throw new BadRequestException("Unexpected post id");
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
        if (durationBegin != null) {
            post.setDuration_begin(durationBegin);
        }
        if (durationEnd != null) {
            post.setDuration_end(durationEnd);
        }
        if (postPublished != null) {
            post.setPost_published(postPublished);
        }
        if (postEnabled != null) {
            post.setPost_enabled(postEnabled);
        }

        if (!post.checkAllFieldsIsNull()) {
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
        } else {
            throw new BadRequestException("Unexpected post");
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
        if (durationBegin != null) {
            post.setDuration_begin(durationBegin);
        }
        if (durationEnd != null) {
            post.setDuration_end(durationEnd);
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