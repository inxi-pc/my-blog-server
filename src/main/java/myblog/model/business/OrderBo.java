package myblog.model.business;

/**
 * Sql Order statement
 *
 */
public class OrderBo {

    /**
     * Order By any column
     */
    private String order_by;

    /**
     * Order by Desc or ASC
     *
     */
    private String order_type;

    public OrderBo(String orderBy, String orderType) {
        this.setOrder_by(orderBy);
        this.setOrder_type(orderType);
    }

    /**
     * Guarantee this.order_by is valid
     *
     * @param orderBy
     */
    public void setOrder_by(String orderBy) {
        this.order_by = orderBy != null
                && orderBy.length() > 0 ? orderBy : "id";
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
