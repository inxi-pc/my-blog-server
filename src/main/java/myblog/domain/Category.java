package myblog.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import myblog.Helper;
import myblog.annotation.PrimaryKey;

import java.util.Date;

public class Category extends Domain {

    @PrimaryKey
    private Integer category_id;
    private Integer category_parent_id;
    private Integer category_root_id;
    private String category_name_en;
    private String category_name_cn;
    private Integer category_level;
    private String category_created_at;
    private String category_updated_at;
    private Boolean category_enabled;

    // Additional fields
    private String duration_begin;
    private String duration_end;

    public Category() {
        this.category_id = null;
        this.category_parent_id = null;
        this.category_root_id = null;
        this.category_name_en = null;
        this.category_name_cn = null;
        this.category_level = null;
        this.category_created_at = null;
        this.category_updated_at = null;
        this.category_enabled = null;
    }

    @JsonCreator
    public Category(@JsonProperty("category_id") Integer categoryId,
                    @JsonProperty("category_parent_id") Integer categoryParentId,
                    @JsonProperty("category_root_id") Integer categoryRootId,
                    @JsonProperty("category_name_en") String categoryNameEn,
                    @JsonProperty("category_name_cn") String categoryNameCn,
                    @JsonProperty("categoryLevel") Integer categoryLevel,
                    @JsonProperty("categoryCreatedAt") String categoryCreatedAt,
                    @JsonProperty("categoryUpdatedAt") String categoryUpdatedAt,
                    @JsonProperty("categoryEnabled") Boolean categoryEnabled) {
        this.category_id = categoryId;
        this.category_parent_id = categoryParentId;
        this.category_root_id = categoryRootId;
        this.category_name_cn = categoryNameCn;
        this.category_name_en = categoryNameEn;
        this.category_level = categoryLevel;
        this.category_created_at = categoryCreatedAt;
        this.category_updated_at = categoryUpdatedAt;
        this.category_enabled = categoryEnabled;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public Integer getCategory_parent_id() {
        return category_parent_id;
    }

    public Integer getCategory_root_id() {
        return category_root_id;
    }

    public String getCategory_name_en() {
        return category_name_en;
    }

    public String getCategory_name_cn() {
        return category_name_cn;
    }

    public Integer getCategory_level() {
        return category_level;
    }

    public String getCategory_created_at() {
        return category_created_at;
    }

    public String getCategory_updated_at() {
        return category_updated_at;
    }

    public Boolean getCategory_enabled() {
        return category_enabled;
    }

    @JsonIgnore
    public String getDuration_begin() {
        return duration_begin;
    }

    @JsonIgnore
    public String getDuration_end() {
        return duration_end;
    }

    public static boolean isValidCategoryId(Integer categoryId) {
        return categoryId != null && categoryId > 0;
    }

    public static boolean isValidCategoryParentId(Integer categoryParentId) {
        return categoryParentId == null || categoryParentId > 0;
    }

    public static boolean isValidCategoryRootId(Integer categoryRootId) {
        return categoryRootId == null || categoryRootId > 0;
    }

    // todo: add needed condition, like length, like sql injection, etc.
    public static boolean isValidCategoryName(String categoryName) {
        return categoryName == null || true;
    }

    public static boolean isValidCategoryLevel(Integer categoryLevel) {
        return categoryLevel != null && categoryLevel > 0;
    }

    public static boolean isValidCategoryCreatedAt(String categoryCreatedAt) {
        return categoryCreatedAt != null;
    }

    public static boolean isValidCategoryUpdatedAt(String categoryUpdatedAt) {
        return categoryUpdatedAt != null;
    }

    public static boolean isValidCategoryEnabled(Boolean categoryEnabled) {
        return categoryEnabled != null;
    }

    public static boolean isValidDurationBegin(String durationBegin) {
        return durationBegin != null;
    }

    public static Boolean isValidDurationEnd(String durationEnd) {
        return durationEnd != null;
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
     * @param categoryParentId
     */
    public void setCategory_parent_id(Integer categoryParentId) {
        if (isValidCategoryParentId(categoryParentId)) {
            this.category_parent_id = categoryParentId;
        } else {
            throw new IllegalArgumentException("Unexpected category parent id");
        }
    }

    /**
     * Not null
     *
     * @param categoryRootId
     */
    public void setCategory_root_id(Integer categoryRootId) {
        if (isValidCategoryRootId(categoryRootId)) {
            this.category_root_id = categoryRootId;
        } else {
            throw new IllegalArgumentException("Unexpected category root id");
        }
    }

    /**
     * Nullable
     *
     * @param categoryNameEn
     */
    public void setCategory_name_en(String categoryNameEn) {
        if (isValidCategoryName(categoryNameEn)) {
            this.category_name_en = categoryNameEn;
        } else {
            throw new IllegalArgumentException("Unexpected category name en");
        }
    }

    /**
     * Nullable
     *
     * @param categoryNameCn
     */
    public void setCategory_name_cn(String categoryNameCn) {
        if (isValidCategoryName(categoryNameCn)) {
            this.category_name_cn = categoryNameCn;
        } else {
            throw new IllegalArgumentException("Unexpected category name cn");
        }
    }

    /**
     * Not null, has default value
     *
     * @param categoryLevel
     */
    public void setCategory_level(Integer categoryLevel) {
        if (isValidCategoryLevel(categoryLevel)) {
            this.category_level = categoryLevel;
        } else {
            throw new IllegalArgumentException("Unexpected category level");
        }
    }

    /**
     * Not null, has default value
     *
     * @param categoryEnabled
     */
    public void setCategory_enabled(Boolean categoryEnabled) {
        if (isValidCategoryEnabled(categoryEnabled)) {
            this.category_enabled = categoryEnabled;
        } else {
            this.category_enabled = false;
        }
    }

    /**
     * Not null, has default value
     *
     * @param categoryCreatedAt
     */
    public void setCategory_created_at(String categoryCreatedAt) {
        if (isValidCategoryCreatedAt(categoryCreatedAt)) {
            this.category_created_at = categoryCreatedAt;
        } else {
            this.category_created_at = Helper.formatDatetimeUTC(new Date());
        }
    }

    /*
     * Not null, has default value
     *
     * @param categoryUpdatedAt
     */
    public void setCategory_updated_at(String categoryUpdatedAt) {
        if (isValidCategoryUpdatedAt(categoryUpdatedAt)) {
            this.category_updated_at = categoryUpdatedAt;
        } else {
            this.category_updated_at = Helper.formatDatetimeUTC(new Date());
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
        if (isValidDurationEnd(durationEnd)) {
            this.duration_end = durationEnd;
        } else {
            this.duration_end = Helper.formatDatetimeUTC(new Date());
        }
    }
}
