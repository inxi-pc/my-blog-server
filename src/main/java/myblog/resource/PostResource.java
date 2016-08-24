package myblog.resource;

import myblog.model.Post;
import myblog.service.PostService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@Path("/posts")
public class PostResource {

    @GET
    @Produces("application/json")
    public List<Post> getPostList() {
        return new ArrayList<Post>();
    }

    @GET
    @Path("/{postId}")
    @Produces("application/json")
    public Post getPost(@PathParam("postId") int postId) {
        return PostService.getPostById(postId);
    }
}