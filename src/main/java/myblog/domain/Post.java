package myblog.domain;

import myblog.Helper;
import myblog.annotation.Insertable;
import myblog.annotation.OuterSettable;
import myblog.annotation.PrimaryKey;
import myblog.annotation.Updatable;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;

import javax.ws.rs.core.Response;
import java.util.Date;

public class Post extends Domain {

    @PrimaryKey
    private Integer post_id;

    @Insertable(nullable = false)
    @Updatable
    @OuterSettable
    private Integer category_id;

    @Insertable(nullable = false)
    @Updatable
    @OuterSettable
    private Integer user_id;

    @Insertable(nullable = true)
    @Updatable
    @OuterSettable
    private String post_title;

    @Insertable(nullable = true)
    @Updatable
    @OuterSettable
    private String post_content;

    @Insertable(nullable = false, defaultable = true)
    @Updatable
    @OuterSettable
    private Boolean post_published;

    @Insertable(nullable = false, defaultable = true)
    @Updatable
    private Boolean post_enabled;

    @Insertable(nullable = false, defaultable = true)
    private String post_created_at;

    @Insertable(nullable = false, defaultable = true)
    @Updatable
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

    public static boolean isValidPostId(Integer postId) {
        return postId == null || postId > 0;
    }

    public static boolean isValidCategoryId(Integer categoryId) {
        return categoryId == null || categoryId > 0;
    }

    public static boolean isValidUserId(Integer userId) {
        return userId == null || userId > 0;
    }

    public static boolean isValidPostTitle(String postTitle) {
        return postTitle == null || postTitle.length() <= 100;
    }

    // TODO: 16/11/18
    // add more validation
    public static boolean isValidPostContent(String postContent) {
        if (postContent == null) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean isValidPostCreatedAt(String postCreatedAt) {
        return postCreatedAt == null || Helper.isValidDataTimeFormat(postCreatedAt);
    }

    public static boolean isValidPostUpdatedAt(String postUpdatedAt) {
        return postUpdatedAt == null || Helper.isValidDataTimeFormat(postUpdatedAt);
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer postId) {
        if (isValidPostId(postId)) {
            this.post_id = postId;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "post_id", Response.Status.BAD_REQUEST);
        }
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer categoryId) {
        if (isValidCategoryId(categoryId)) {
            this.category_id = categoryId;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "category_id", Response.Status.BAD_REQUEST);
        }
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer userId) {
        if (isValidUserId(userId)) {
            this.user_id = userId;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "user_id", Response.Status.BAD_REQUEST);
        }
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String postTitle) {
        if (isValidPostTitle(postTitle)) {
            this.post_title = postTitle;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "post_title", Response.Status.BAD_REQUEST);
        }
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String postContent) {
        if (isValidPostContent(postContent)) {
            this.post_content = postContent;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "post_content", Response.Status.BAD_REQUEST);
        }
    }

    public Boolean getPost_published() {
        return post_published;
    }

    public void setPost_published(Boolean postPublished) {
        this.post_published = postPublished;
    }

    public Boolean getPost_enabled() {
        return post_enabled;
    }

    public void setPost_enabled(Boolean postEnabled) {
        this.post_enabled = postEnabled;
    }

    public String getPost_created_at() {
        return post_created_at;
    }

    public void setPost_created_at(String postCreatedAt) {
        if (isValidPostCreatedAt(postCreatedAt)) {
            this.post_created_at = postCreatedAt;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "post_created_at", Response.Status.BAD_REQUEST);
        }
    }

    public String getPost_updated_at() {
        return post_updated_at;
    }

    public void setPost_updated_at(String postUpdatedAt) {
        if (isValidPostUpdatedAt(postUpdatedAt)) {
            this.post_updated_at = postUpdatedAt;
        } else {
            throw new GenericException(GenericMessageMeta.INVALID_VALUE_FIELD, "post_updated_at", Response.Status.BAD_REQUEST);
        }
    }

    public void setDefaultPost_published() {
        this.post_published = false;
    }

    public void setDefaultPost_enabled() {
        this.post_enabled = true;
    }

    public void setDefaultPost_created_at() {
        this.post_created_at = Helper.formatDatetimeUTC(new Date());
    }

    public void setDefaultPost_updated_at() {
        this.post_updated_at = Helper.formatDatetimeUTC(new Date());
    }
}
