package myblog.model;

import java.util.List;

public class Post extends Model {
    public int post_id;
    public boolean post_published;
    public String post_title;
    public String post_content;
    public int user_id;
    public String post_created_at;
    public String post_updated_at;
    // relate content
    public List<Comment> comments;
}
