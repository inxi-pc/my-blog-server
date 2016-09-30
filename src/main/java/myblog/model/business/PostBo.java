package myblog.model.business;

import myblog.Helper;

import java.util.Date;

public class PostBo {
    public Integer post_id;
    public Integer category_id;
    public Integer user_id;
    public String post_title;
    public String post_content;
    public Boolean post_published;
    public Boolean post_enabled;
    public String post_created_at;
    public String post_updated_at;

    public void checkCreateObject() throws Exception {
        if (this.category_id == null || this.user_id == null) {
            throw new Exception("category_id and user_id is required");
        }

        String now = Helper.formatDatetimeUTC(new Date());
        this.post_enabled = this.post_enabled != null ? this.post_enabled : true;
        this.post_published = this.post_published != null ? this.post_published : false;
        this.post_created_at = this.post_created_at == null ? now : this.post_created_at;
        this.post_updated_at = this.post_updated_at == null ? now : this.post_updated_at;
    }

    public void checkUpdateObject() throws Exception {
        if (this.post_id == null) {
            throw new Exception("post_id is required");
        }
    }
}
