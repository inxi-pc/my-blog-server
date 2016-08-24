package myblog.model;

public class Comment extends Model {
    public int comment_id;
    public String comment_content;
    public int user_id;
    public String comment_created_at;
    public String comment_updated_at;

    // relate content
    public User user;
}
