package myblog.resource;

import myblog.model.Post;
import myblog.service.PostService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/posts")
public class PostResource {

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public int createPost(@FormParam("user_id") int user_id,
                          @FormParam("post_title") String post_title,
                          @FormParam("post_content") String post_content,
                          @FormParam("post_published") boolean post_published) {
        Post insert = new Post();
        insert.user_id = user_id;
        insert.post_title = post_title;
        insert.post_content = post_content;
        insert.post_published = post_published;

        return PostService.createPost(insert);
    }

    @GET
    @Path("/{postId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post getPost(@PathParam("postId") int postId) {
        return PostService.getPostById(postId);
    }
}