package myblog.domain;

import myblog.Helper;
import myblog.annotation.Insertable;
import myblog.annotation.OuterSettable;
import myblog.annotation.PrimaryKey;
import myblog.annotation.Updatable;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.exception.LiteralMessageMeta;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class Category extends Domain {

    @PrimaryKey
    private Integer category_id;

    @Insertable(nullable = true)
    @OuterSettable
    private Integer category_parent_id;

    @Insertable(nullable = true)
    private Integer category_root_id;

    @Insertable(nullable = true)
    @Updatable
    @OuterSettable
    private String category_name_en;

    @Insertable(nullable = true)
    @Updatable
    @OuterSettable
    private String category_name_cn;

    @Insertable(nullable = true)
    private Integer category_level;

    @Insertable(nullable = false)
    private String category_created_at;

    @Insertable(nullable = false)
    @Updatable
    private String category_updated_at;

    @Insertable(nullable = false)
    @Updatable
    private Boolean category_enabled;

    private List<Category> children;

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
        this.children = null;
    }

    public static List<Category> formatCategoryTree(List<Category> categories) {
        List<Category> rootCategories = new ArrayList<Category>();
        List<Category> childCategories = new ArrayList<Category>();
        List<Category> grandChildCategories = new ArrayList<Category>();

        for (Category category : categories) {
            if (category.category_level == 1) {
                rootCategories.add(category);
            }
            if (category.category_level == 2) {
                childCategories.add(category);
            }
            if (category.category_level == 3) {
                grandChildCategories.add(category);
            }
        }

        createTree(childCategories, grandChildCategories.iterator());
        createTree(rootCategories, childCategories.iterator());

        return rootCategories;
    }

    private static void createTree(List<Category> parent, Iterator<Category> children) {
        while (children.hasNext()) {
            Category category = children.next();
            for (Category categoryParent : parent) {
                if (category.category_parent_id.equals(categoryParent.category_id)) {
                    if (categoryParent.children == null) {
                        categoryParent.children = new ArrayList<Category>();
                    }
                    categoryParent.children.add(category);
                    children.remove();
                }
            }
        }

        if (children.hasNext()) {
            throw new GenericException(LiteralMessageMeta.LACK_PARENT_CATEGORY_TO_GEN_TREE, Response.Status.INTERNAL_SERVER_ERROR);
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

    public List getChildren() {
        return this.children;
    }

    public void setCategory_id(Integer categoryId) {
        if (isValidCategoryId(categoryId)) {
            this.category_id = categoryId;
        } else {
            throw new GenericException(
                    GenericMessageMeta.NOT_VALID_VALUE_FIELD,
                    "category_id",
                    Response.Status.BAD_REQUEST);
        }
    }

    public void setCategory_parent_id(Integer categoryParentId) {
        if (isValidCategoryParentId(categoryParentId)) {
            this.category_parent_id = categoryParentId;
        } else {
            throw new GenericException(
                    GenericMessageMeta.NOT_VALID_VALUE_FIELD,
                    "category_parent_id",
                    Response.Status.BAD_REQUEST);
        }
    }

    public void setCategory_root_id(Integer categoryRootId) {
        if (isValidCategoryRootId(categoryRootId)) {
            this.category_root_id = categoryRootId;
        } else {
            throw new GenericException(
                    GenericMessageMeta.NOT_VALID_VALUE_FIELD,
                    "category_root_id",
                    Response.Status.BAD_REQUEST);
        }
    }

    public void setCategory_name_en(String categoryNameEn) {
        if (isValidCategoryName(categoryNameEn)) {
            this.category_name_en = categoryNameEn;
        } else {
            throw new GenericException(
                    GenericMessageMeta.NOT_VALID_VALUE_FIELD,
                    "category_name_en",
                    Response.Status.BAD_REQUEST);
        }
    }

    public void setCategory_name_cn(String categoryNameCn) {
        if (isValidCategoryName(categoryNameCn)) {
            this.category_name_cn = categoryNameCn;
        } else {
            throw new GenericException(
                    GenericMessageMeta.NOT_VALID_VALUE_FIELD,
                    "category_name_cn",
                    Response.Status.BAD_REQUEST);
        }
    }

    public void setCategory_level(Integer categoryLevel) {
        if (isValidCategoryLevel(categoryLevel)) {
            this.category_level = categoryLevel;
        } else {
            throw new GenericException(
                    GenericMessageMeta.NOT_VALID_VALUE_FIELD,
                    "category_level",
                    Response.Status.BAD_REQUEST);
        }
    }

    public void setCategory_enabled(Boolean categoryEnabled) {
        this.category_enabled = categoryEnabled;
    }

    public void setCategory_created_at(String categoryCreatedAt) {
        if (isValidCategoryCreatedAt(categoryCreatedAt)) {
            this.category_created_at = categoryCreatedAt;
        } else {
            throw new GenericException(
                    GenericMessageMeta.NOT_VALID_VALUE_FIELD,
                    "category_created_at",
                    Response.Status.BAD_REQUEST);
        }
    }

    public void setCategory_updated_at(String categoryUpdatedAt) {
        if (isValidCategoryUpdatedAt(categoryUpdatedAt)) {
            this.category_updated_at = categoryUpdatedAt;
        } else {
            throw new GenericException(
                    GenericMessageMeta.NOT_VALID_VALUE_FIELD,
                    "category_updated_at",
                    Response.Status.BAD_REQUEST);
        }
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public void setDefaultCategory_enabled() {
        this.category_enabled = true;
    }

    public void setDefaultCategory_created_at() {
        this.category_created_at = Helper.formatDatetimeUTC(new Date());
    }

    public void setDefaultCategory_updated_at() {
        this.category_updated_at = Helper.formatDatetimeUTC(new Date());
    }

    public static boolean isValidCategoryId(Integer categoryId) {
        return categoryId == null || categoryId > 0;
    }

    public static boolean isValidCategoryParentId(Integer categoryParentId) {
        return categoryParentId == null || categoryParentId > 0;
    }

    public static boolean isValidCategoryRootId(Integer categoryRootId) {
        return categoryRootId == null || categoryRootId > 0;
    }

    public static boolean isValidCategoryName(String categoryName) {
        if (categoryName == null) {
            return true;
        } else {
            Pattern p = Pattern.compile("[\\u4E00-\\u9FA5A-Za-z0-9_]{0,100}");

            return p.matcher(categoryName).matches();
        }
    }

    public static boolean isValidCategoryLevel(Integer categoryLevel) {
        return categoryLevel == null || categoryLevel >= 1;
    }

    public static boolean isValidCategoryCreatedAt(String categoryCreatedAt) {
        return categoryCreatedAt == null || Helper.isValidDataTimeFormat(categoryCreatedAt);
    }

    public static boolean isValidCategoryUpdatedAt(String categoryUpdatedAt) {
        return categoryUpdatedAt == null || Helper.isValidDataTimeFormat(categoryUpdatedAt);
    }
}
