package myblog.resource;

import myblog.model.Post;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Vector;

@Path("/posts")
public class PostResource {

    @GET
    @Produces("application/json")
    public Vector<Post> getPostList() {
        return new Vector<Post>();
    }

    @GET
    @Path("/{postId}")
    @Produces("application/json")
    public Post getPost(@PathParam("postId") int postId) {
        return new Post();
    }
}