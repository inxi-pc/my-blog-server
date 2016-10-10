package myblog.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import myblog.Helper;
import myblog.annotation.PrimaryKey;

import javax.ws.rs.InternalServerErrorException;
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
     * @param post_id
     */
    public void setPost_id(Integer post_id) {
        if (isValidPostId(post_id)) {
            this.post_id = post_id;
        } else {
            throw new InternalServerErrorException();
        }
    }

    /**
     * Not null
     *
     * @param category_id
     */
    public void setCategory_id(Integer category_id) {
        if (isValidCategoryId(category_id)) {
            this.category_id = category_id;
        } else {
            throw new InternalServerErrorException();
        }
    }

    /**
     * Not null
     *
     * @param user_id
     */
    public void setUser_id(Integer user_id) {
        if (isValidUserId(user_id)) {
            this.user_id = user_id;
        } else {
            throw new InternalServerErrorException();
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
        if (isValidPostPublished(post_published)) {
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
        if (isValidPostEnabled(post_enabled)) {
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
        if (isValidPostCreatedAt(post_created_at)) {
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
        if (isValidPostUpdatedAt(post_updated_at)) {
            this.post_updated_at = post_updated_at;
        } else {
            this.post_updated_at = Helper.formatDatetimeUTC(new Date());
        }
    }

    @JsonIgnore
    public void setDuration_begin(String duration_begin) {
        if (isValidDurationBegin(duration_begin)) {
            this.duration_begin = duration_begin;
        } else {
            this.duration_begin = Helper.formatDatetimeUTC(new Date());
        }
    }

    @JsonIgnore
    public void setDuration_end(String duration_end) {
        if (isValidDurationBegin(duration_end)) {
            this.duration_end = duration_end;
        } else {
            this.duration_end = Helper.formatDatetimeUTC(new Date());
        }
    }
}
