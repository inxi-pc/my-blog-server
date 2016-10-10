package myblog.domain;

import myblog.annotation.PrimaryKey;

public class Comment {

    @PrimaryKey
    public int comment_id;
    public String comment_content;
    public int user_id;
    public String comment_created_at;
    public String comment_updated_at;
}
