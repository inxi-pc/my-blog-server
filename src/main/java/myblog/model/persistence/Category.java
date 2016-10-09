package myblog.model.persistence;

import myblog.Helper;
import myblog.model.annotation.PrimaryKey;

import java.util.Date;

public class Category {

    @PrimaryKey
    private Integer category_id;
    private String category_name_en;
    private String category_name_cn;
    private Integer category_level;
    private String category_created_at;
    private String category_updated_at;
    private Boolean category_enabled;

    public Category() {
        this.category_id = null;
        this.category_name_en = null;
        this.category_name_cn = null;
        this.category_level = null;
        this.category_created_at = null;
        this.category_updated_at = null;
        this.category_enabled = null;
    }

    public Integer getCategory_id() {
        return category_id;
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

    /**
     * Not null
     *
     * @param category_id
     * @throws Exception
     */
    public void setCategory_id(Integer category_id) throws Exception {
        if (category_id != null && category_id > 0) {
            this.category_id = category_id;
        } else {
            throw new Exception("Set a invalid value in category.category_id");
        }
    }

    public void setCategory_name_en(String category_name_en) {
        this.category_name_en = category_name_en;
    }

    public void setCategory_name_cn(String category_name_cn) {
        this.category_name_cn = category_name_cn;
    }

    /**
     * Not null, has default value
     *
     * @param category_level
     */
    public void setCategory_level(Integer category_level) {
        if (category_level != null && category_level > 0) {
            this.category_level = category_level;
        } else {
            this.category_level = 1;
        }
    }

    /**
     * Not null, has default value
     *
     * @param category_enabled
     */
    public void setCategory_enabled(Boolean category_enabled) {
        if (category_enabled != null) {
            this.category_enabled = category_enabled;
        } else {
            this.category_enabled = false;
        }
    }

    /**
     * Not null, has default value
     *
     * @param category_created_at
     */
    public void setCategory_created_at(String category_created_at) {
        if (category_created_at != null) {
            this.category_created_at = category_created_at;
        } else {
            this.category_created_at = Helper.formatDatetimeUTC(new Date());
        }
    }

    /**
     * Not null, has default value
     *
     * @param category_updated_at
     */
    public void setCategory_updated_at(String category_updated_at) {
        if (category_updated_at != null) {
            this.category_updated_at = category_updated_at;
        } else {
            this.category_updated_at = Helper.formatDatetimeUTC(new Date());
        }
    }
}
