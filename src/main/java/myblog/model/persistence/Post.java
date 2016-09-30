package myblog.model.persistence;

import myblog.Helper;

import java.util.Date;

public class Post {
    private Integer post_id;
    private Integer category_id;
    private Integer user_id;
    private String post_title;
    private String post_content;
    private Boolean post_published;
    private Boolean post_enabled;
    private String post_created_at;
    private String post_updated_at;

    public Post() {
        this.post_id = null;
        this.category_id = null;
        this.user_id = null;
        this.post_title = null;
        this.post_content = null;
        this.post_published = null;
        this.post_enabled = null;
        this.post_created_at = null;
        this.post_updated_at = null;
    }

    /**
     * Not null
     *
     * @param post_id
     */
    public void setPost_id(Integer post_id) throws Exception {
        if (post_id != null && post_id > 0) {
            this.post_id = post_id;
        } else {
            throw new Exception("Set a invalid value in Post.post_id");
        }
    }

    /**
     * Not null
     *
     * @param category_id
     */
    public void setCategory_id(Integer category_id) throws Exception {
        if (category_id != null && category_id > 0) {
            this.category_id = category_id;
        } else {
            throw new Exception("Set a invalid value in Post.category_id");
        }
    }

    /**
     * Not null
     *
     * @param user_id
     */
    public void setUser_id(Integer user_id) throws Exception {
        if (user_id != null && user_id > 0) {
            this.user_id = user_id;
        } else {
            throw new Exception("Set a invalid value in Post.user_id");
        }
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    /**
     * Not null, has default value
     *
     * @param post_published
     */
    public void setPost_published(Boolean post_published) {
        if (post_published != null) {
            this.post_published = post_published;
        } else {
            this.post_published = false;
        }
    }

    /**
     * Not null, has default value
     *
     * @param post_enabled
     */
    public void setPost_enabled(Boolean post_enabled) {
        if (post_enabled != null) {
            this.post_enabled = post_enabled;
        } else {
            this.post_enabled = false;
        }
    }

    /**
     * Not null, has default value
     *
     * @param post_created_at
     */
    public void setPost_created_at(String post_created_at) {
        if (post_created_at != null) {
            this.post_created_at = post_created_at;
        } else {
            this.post_created_at = Helper.formatDatetimeUTC(new Date());
        }
    }

    /**
     * Not null, has default value
     *
     * @param post_updated_at
     */
    public void setPost_updated_at(String post_updated_at) {
        if (post_updated_at != null) {
            this.post_updated_at = post_updated_at;
        } else {
            this.post_updated_at = Helper.formatDatetimeUTC(new Date());
        }
    }

    public Integer getPost_id() {
        return post_id;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getPost_title() {
        return post_title;
    }

    public String getPost_content() {
        return post_content;
    }

    public Boolean getPost_published() {
        return post_published;
    }

    public Boolean getPost_enabled() {
        return post_enabled;
    }

    public String getPost_created_at() {
        return post_created_at;
    }

    public String getPost_updated_at() {
        return post_updated_at;
    }
}
