package myblog.domain;

import myblog.Helper;
import myblog.annotation.Insertable;
import myblog.annotation.OuterSettable;
import myblog.annotation.PrimaryKey;
import myblog.annotation.Updatable;
import myblog.exception.DomainException;

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

    public static boolean isValidPostId(Integer postId) {
        if (postId == null) {
            return true;
        } else {
            return postId > 0;
        }
    }

    public static boolean isValidCategoryId(Integer categoryId) {
        if (categoryId == null) {
            return true;
        } else {
            return categoryId > 0;
        }
    }

    public static boolean isValidUserId(Integer userId) {
        if (userId == null) {
            return true;
        } else {
            return userId > 0;
        }
    }

    public static boolean isValidPostTitle(String postTitle) {
        if (postTitle == null) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean isValidPostContent(String postContent) {
        if (postContent == null) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean isValidPostPublished(Boolean postPublished) {
        if (postPublished == null) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean isValidPostEnabled(Boolean postEnabled) {
        if (postEnabled == null) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean isValidPostCreatedAt(String postCreatedAt) {
        if (postCreatedAt == null) {
            return true;
        } else {
            return true;
        }
    }

    public static boolean isValidPostUpdatedAt(String postUpdatedAt) {
        if (postUpdatedAt == null) {
            return true;
        } else {
            return true;
        }
    }

    public void setPost_id(Integer postId) {
        if (isValidPostId(postId)) {
            this.post_id = postId;
        } else {
            throw new IllegalArgumentException(
                    new DomainException("post_id", DomainException.Type.FIELD_NOT_VALID_VALUE)
            );
        }
    }

    public void setCategory_id(Integer categoryId) {
        if (isValidCategoryId(categoryId)) {
            this.category_id = categoryId;
        } else {
            throw new IllegalArgumentException(
                    new DomainException("category_id", DomainException.Type.FIELD_NOT_VALID_VALUE)
            );
        }
    }

    public void setUser_id(Integer userId) {
        if (isValidUserId(userId)) {
            this.user_id = userId;
        } else {
            throw new IllegalArgumentException(
                    new DomainException("user_id", DomainException.Type.FIELD_NOT_VALID_VALUE)
            );
        }
    }

    public void setPost_title(String postTitle) {
        if (isValidPostTitle(postTitle)) {
            this.post_title = postTitle;
        } else {
            throw new IllegalArgumentException(
                    new DomainException("post_title", DomainException.Type.FIELD_NOT_VALID_VALUE)
            );
        }
    }

    public void setPost_content(String postContent) {
        if (isValidPostContent(postContent)) {
            this.post_content = postContent;
        } else {
            throw new IllegalArgumentException(
                    new DomainException("post_content", DomainException.Type.FIELD_NOT_VALID_VALUE)
            );
        }
    }

    public void setPost_published(Boolean postPublished) {
        if (isValidPostPublished(postPublished)) {
            this.post_published = postPublished;
        } else {
            throw new IllegalArgumentException(
                    new DomainException("post_published", DomainException.Type.FIELD_NOT_VALID_VALUE)
            );
        }
    }

    public void setPost_enabled(Boolean postEnabled) {
        if (isValidPostEnabled(postEnabled)) {
            this.post_enabled = postEnabled;
        } else {
            throw new IllegalArgumentException(
                    new DomainException("post_enabled", DomainException.Type.FIELD_NOT_VALID_VALUE)
            );
        }
    }

    public void setPost_created_at(String postCreatedAt) {
        if (isValidPostCreatedAt(postCreatedAt)) {
            this.post_created_at = postCreatedAt;
        } else {
            throw new IllegalArgumentException(
                    new DomainException("post_created_at", DomainException.Type.FIELD_NOT_VALID_VALUE)
            );
        }
    }

    public void setPost_updated_at(String postUpdatedAt) {
        if (isValidPostUpdatedAt(postUpdatedAt)) {
            this.post_updated_at = postUpdatedAt;
        } else {
            throw new IllegalArgumentException(
                    new DomainException("post_updated_at", DomainException.Type.FIELD_NOT_VALID_VALUE)
            );
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
