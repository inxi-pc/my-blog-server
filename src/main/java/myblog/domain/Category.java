package myblog.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import myblog.Helper;
import myblog.annotation.Insertable;
import myblog.annotation.Updateable;

import java.util.Date;

public class Category extends Domain {

    private Integer category_id;

    @Insertable(nullable = false)
    @Updateable
    private Integer category_parent_id;

    @Insertable(nullable = false)
    @Updateable
    private Integer category_root_id;

    @Insertable(nullable = true)
    @Updateable
    private String category_name_en;

    @Insertable(nullable = true)
    @Updateable
    private String category_name_cn;

    @Insertable(nullable = false)
    @Updateable
    private Integer category_level;

    @Insertable(nullable = false)
    private String category_created_at;

    @Insertable(nullable = false)
    @Updateable
    private String category_updated_at;

    @Insertable(nullable = false)
    @Updateable
    private Boolean category_enabled;

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
    public Category(@JsonProperty("category_parent_id") Integer categoryParentId,
                    @JsonProperty("category_name_en") String categoryNameEn,
                    @JsonProperty("category_name_cn") String categoryNameCn) {
        if (isValidCategoryParentId(categoryParentId)) {
            this.category_parent_id = categoryParentId;
        }
        if (isValidCategoryName(categoryNameEn)) {
            this.category_name_en = categoryNameEn;
        }
        if (isValidCategoryName(categoryNameCn)) {
            this.category_name_cn = categoryNameCn;
        }
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

    public static boolean isValidCategoryId(Integer categoryId) {
        return categoryId != null && categoryId > 0;
    }

    public static boolean isValidCategoryParentId(Integer categoryParentId) {
        return categoryParentId != null && categoryParentId >= 0;
    }

    public static boolean isValidCategoryRootId(Integer categoryRootId) {
        return isValidCategoryId(categoryRootId);
    }

    public static boolean isValidCategoryName(String categoryName) {
        return categoryName == null || true;
    }

    public static boolean isValidCategoryLevel(Integer categoryLevel) {
        return categoryLevel != null && categoryLevel >= 1;
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

    public void setCategory_id(Integer categoryId) {
        if (isValidCategoryId(categoryId)) {
            this.category_id = categoryId;
        } else {
            throw new IllegalArgumentException("Unexpected category id: Invalid value");
        }
    }

    public void setCategory_parent_id(Integer categoryParentId) {
        if (isValidCategoryParentId(categoryParentId)) {
            this.category_parent_id = categoryParentId;
        } else {
            throw new IllegalArgumentException("Unexpected category parent id: Invalid value");
        }
    }

    public void setCategory_root_id(Integer categoryRootId) {
        if (isValidCategoryRootId(categoryRootId)) {
            this.category_root_id = categoryRootId;
        } else {
            throw new IllegalArgumentException("Unexpected category root id: Invalid value");
        }
    }

    public void setCategory_name_en(String categoryNameEn) {
        if (isValidCategoryName(categoryNameEn)) {
            this.category_name_en = categoryNameEn;
        } else {
            throw new IllegalArgumentException("Unexpected category name en: Invalid value");
        }
    }

    public void setCategory_name_cn(String categoryNameCn) {
        if (isValidCategoryName(categoryNameCn)) {
            this.category_name_cn = categoryNameCn;
        } else {
            throw new IllegalArgumentException("Unexpected category name cn: Invalid value");
        }
    }

    public void setCategory_level(Integer categoryLevel) {
        if (isValidCategoryLevel(categoryLevel)) {
            this.category_level = categoryLevel;
        } else {
            throw new IllegalArgumentException("Unexpected category level: Invalid value");
        }
    }

    public void setCategory_enabled(Boolean categoryEnabled) {
        if (isValidCategoryEnabled(categoryEnabled)) {
            this.category_enabled = categoryEnabled;
        } else {
            this.category_enabled = true;
        }
    }

    public void setCategory_created_at(String categoryCreatedAt) {
        if (isValidCategoryCreatedAt(categoryCreatedAt)) {
            this.category_created_at = categoryCreatedAt;
        } else {
            this.category_created_at = Helper.formatDatetimeUTC(new Date());
        }
    }

    public void setCategory_updated_at(String categoryUpdatedAt) {
        if (isValidCategoryUpdatedAt(categoryUpdatedAt)) {
            this.category_updated_at = categoryUpdatedAt;
        } else {
            this.category_updated_at = Helper.formatDatetimeUTC(new Date());
        }
    }
}
