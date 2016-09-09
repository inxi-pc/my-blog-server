package myblog.model;

/**
 * Sql Order statement
 *
 */
public class SqlOrder {

    /**
     * Order By any column
     */
    private String orderBy;

    /**
     * Order by Desc or ASC
     *
     */
    private OrderType orderType;

    private enum OrderType { DESC, ASC };

    public SqlOrder(String order, String orderType) {
        this.setOrder(order);
        this.setOrderType(orderType);
    }

    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Guarantee this.orderBy is valid
     * @param orderBy
     */
    public void setOrder(String orderBy) {
        this.orderBy = orderBy != null
                && orderBy.length() > 0 ? orderBy : "id";
    }

    public OrderType getOrderType() {
        return orderType;
    }

    /**
     * Guarantee this.orderType is valid
     * @param orderType
     */
    public void setOrderType(String orderType) {
        if (orderType != null && orderType.equals("DESC")) {
            this.orderType = OrderType.DESC;
        } else if (orderType != null && orderType.equals("ASC")) {
            this.orderType = OrderType.ASC;
        } else {
            this.orderType = OrderType.DESC;
        }
    }
}
