package myblog.domain;

import myblog.annotation.PrimaryKey;

import java.lang.reflect.Field;

/**
 * Sql Sort statement
 *
 */
public class Sort<T> extends Domain {

    /**
     * Sort By any column
     */
    private String order_by;

    /**
     * Sort by Desc or ASC
     */
    private String order_type;

    public Sort(String orderBy, String orderType, Class<T> cls) {
        this.setOrder_by(orderBy, cls);
        this.setOrder_type(orderType);
    }

    /**
     * Guarantee this.order_by is valid
     *
     * @param orderBy
     */
    public void setOrder_by(String orderBy, Class<T> cls) {
        if (orderBy != null) {
            this.order_by = orderBy;
        } else {
            for (Field field : cls.getFields()) {
                if (field.isAnnotationPresent(PrimaryKey.class)) {
                    this.order_by = field.getName();
                }
            }
        }
    }

    /**
     * Guarantee this.order_type is valid
     *
     * @param orderType
     */
    public void setOrder_type(String orderType) {
        if (orderType != null) {
            if (orderType.equalsIgnoreCase("DESC")) {
                this.order_type = "DESC";
            } else {
                this.order_type = "ASC";
            }
        } else {
            this.order_type = "DESC";
        }
    }

    public String getOrder_by() {
        return this.order_by;
    }

    public String getOrder_type() {
        return this.order_type;
    }
}


