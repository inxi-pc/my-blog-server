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

    public void setDefaultValue() {
        String now = Helper.formatDatetimeUTC(new Date());
        this.post_enabled = this.post_enabled != null;
        this.post_published = this.post_published != null;
        this.post_created_at = this.post_created_at == null ? now : this.post_created_at;
        this.post_updated_at = this.post_updated_at == null ? now : this.post_updated_at;
    }
}
