package myblog.domain;

import myblog.annotation.PrimaryKey;

import java.lang.reflect.Field;

/**
 * Sql Order statement
 *
 */
public class Order<T> extends Domain {

    /**
     * Order By any column
     */
    private String order_by;

    /**
     * Order by Desc or ASC
     *
     */
    private String order_type;

    public Order(String orderBy, String orderType, Class<T> cls) {
        this.setOrder_by(orderBy, cls);
        this.setOrder_type(orderType);
    }

    /**
     * Guarantee this.order_by is valid
     *
     * @param orderBy
     */
    public void setOrder_by(String orderBy, Class<T> cls) {
        if (orderBy == null) {
            for (Field field : cls.getFields()) {
                if (field.isAnnotationPresent(PrimaryKey.class)) {
                    this.order_by = field.getName();
                }
            }
        } else {
            this.order_by = orderBy;
        }
    }

    /**
     * Guarantee this.order_type is valid
     *
     * @param orderType
     */
    public void setOrder_type(String orderType) {
        if (orderType != null && orderType.equals("DESC")) {
            this.order_type = orderType;
        } else if (orderType != null && orderType.equals("ASC")) {
            this.order_type = orderType;
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
