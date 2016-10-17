package myblog.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import myblog.Helper;
import myblog.annotation.PrimaryKey;

import java.util.Date;

public class Post extends Domain {

    @PrimaryKey
    private Integer post_id;
    private Integer category_id;
    private Integer user_id;
    private String post_title;
    private String post_content;
    private Boolean post_published;
    private Boolean post_enabled;
    private String post_created_at;
    private String post_updated_at;

    // Additional fields
    private String duration_begin;
    private String duration_end;

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

    @JsonCreator
    public Post(@JsonProperty("post_id") Integer postId,
                @JsonProperty("category_id") Integer categoryId,
                @JsonProperty("user_id") Integer userId,
                @JsonProperty("post_title") String postTitle,
                @JsonProperty("post_content") String postContent,
                @JsonProperty("post_published") Boolean postPublished,
                @JsonProperty("post_enabled") Boolean postEnabled,
                @JsonProperty("post_created_at") String postCreatedAt,
                @JsonProperty("post_updated_at") String postUpdatedAt) {
        this.post_id = postId;
        this.category_id = categoryId;
        this.user_id = userId;
        this.post_title = postTitle;
        this.post_content = postContent;
        this.post_published = postPublished;
        this.post_enabled = postEnabled;
        this.post_created_at = postCreatedAt;
        this.post_updated_at = postUpdatedAt;
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

    @JsonIgnore
    public String getDuration_begin() {
        return duration_begin;
    }

    @JsonIgnore
    public String getDuration_end() {
        return duration_end;
    }

    public static boolean isValidPostId(Integer postId) {
        return postId != null && postId > 0;
    }

    public static boolean isValidCategoryId(Integer categoryId) {
        return categoryId != null && categoryId > 0;
    }

    public static boolean isValidUserId(Integer userId) {
        return userId != null && userId > 0;
    }

    // todo: add needed condition
    public static boolean isValidPostTitle(String postTitle) {
        return postTitle == null || true;
    }

    // todo: add needed condition
    public static boolean isValidPostContent(String postContent) {
        return postContent == null || true;
    }

    public static boolean isValidPostPublished(Boolean postPublished) {
        return postPublished != null;
    }

    public static boolean isValidPostEnabled(Boolean postEnabled) {
        return postEnabled != null;
    }

    public static boolean isValidPostCreatedAt(String postCreatedAt) {
        return postCreatedAt != null;
    }

    public static boolean isValidPostUpdatedAt(String postUpdatedAt) {
        return postUpdatedAt != null;
    }

    public static boolean isValidDurationBegin(String durationBegin) {
        return durationBegin != null;
    }

    public static boolean isValidDurationEnd(String durationEnd) {
        return durationEnd != null;
    }

    /**
     * Not null
     *
     * @param postId
     */
    public void setPost_id(Integer postId) {
        if (isValidPostId(postId)) {
            this.post_id = postId;
        } else {
            throw new IllegalArgumentException("Unexpected post id");
        }
    }

    /**
     * Not null
     *
     * @param categoryId
     */
    public void setCategory_id(Integer categoryId) {
        if (isValidCategoryId(categoryId)) {
            this.category_id = categoryId;
        } else {
            throw new IllegalArgumentException("Unexpected category id");
        }
    }

    /**
     * Not null
     *
     * @param userId
     */
    public void setUser_id(Integer userId) {
        if (isValidUserId(userId)) {
            this.user_id = userId;
        } else {
            throw new IllegalArgumentException("Unexpected user id");
        }
    }

    /**
     * Nullable
     *
     * @param postTitle
     */
    public void setPost_title(String postTitle) {
        if (isValidPostTitle(postTitle)) {
            this.post_title = postTitle;
        } else {
            throw new IllegalArgumentException("Unexpected post title");
        }
    }

    /**
     * Nullable
     *
     * @param postContent
     */
    public void setPost_content(String postContent) {
        if (isValidPostContent(postContent)) {
            this.post_content = postContent;
        } else {
            throw new IllegalArgumentException("Unexpected post content");
        }
    }

    /**
     * Not null, has default value
     *
     * @param postPublished
     */
    public void setPost_published(Boolean postPublished) {
        if (isValidPostPublished(postPublished)) {
            this.post_published = postPublished;
        } else {
            this.post_published = false;
        }
    }

    /**
     * Not null, has default value
     *
     * @param postEnabled
     */
    public void setPost_enabled(Boolean postEnabled) {
        if (isValidPostEnabled(postEnabled)) {
            this.post_enabled = postEnabled;
        } else {
            this.post_enabled = false;
        }
    }

    /**
     * Not null, has default value
     *
     * @param postCreatedAt
     */
    public void setPost_created_at(String postCreatedAt) {
        if (isValidPostCreatedAt(postCreatedAt)) {
            this.post_created_at = postCreatedAt;
        } else {
            this.post_created_at = Helper.formatDatetimeUTC(new Date());
        }
    }

    /**
     * Not null, has default value
     *
     * @param postUpdatedAt
     */
    public void setPost_updated_at(String postUpdatedAt) {
        if (isValidPostUpdatedAt(postUpdatedAt)) {
            this.post_updated_at = postUpdatedAt;
        } else {
            this.post_updated_at = Helper.formatDatetimeUTC(new Date());
        }
    }

    @JsonIgnore
    public void setDuration_begin(String durationBegin) {
        if (isValidDurationBegin(durationBegin)) {
            this.duration_begin = durationBegin;
        } else {
            this.duration_begin = Helper.formatDatetimeUTC(new Date());
        }
    }

    @JsonIgnore
    public void setDuration_end(String durationEnd) {
        if (isValidDurationBegin(durationEnd)) {
            this.duration_end = durationEnd;
        } else {
            this.duration_end = Helper.formatDatetimeUTC(new Date());
        }
    }
}
