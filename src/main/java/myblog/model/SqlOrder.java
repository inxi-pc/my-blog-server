package myblog.model;

public class SqlOrder {

    private String orderBy;

    private OrderType orderType;

    private enum OrderType { DESC, ASC };

    public SqlOrder(String order, String orderType) {
        this.setOrder(order);
        this.setOrderType(orderType);
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrder(String orderBy) {
        this.orderBy = orderBy != null && orderBy.length() > 0 ? orderBy : "id";
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        if (orderType.equals("DESC")) {
            this.orderType = OrderType.DESC;
        } else if (orderType.equals("ASC")) {
            this.orderType = OrderType.ASC;
        } else {
            this.orderType = OrderType.DESC;
        }
    }
}
