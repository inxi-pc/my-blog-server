package myblog.model.persistent;

import myblog.Helper;

import java.util.Date;

public class Category {
    public Integer category_id;
    public String category_name_en;
    public String category_name_cn;
    public Integer category_level;
    public String category_created_at;
    public String category_updated_at;
    public Boolean category_enabled;

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
