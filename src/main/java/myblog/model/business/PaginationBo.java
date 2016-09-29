package myblog.model.business;

import java.util.ArrayList;
import java.util.List;

/**
 * Sql pagination statement
 *
 */
public class PaginationBo {

    /**
     * Pagination length
     */
    private int limit;

    /**
     * Start Position of Pagination
     */
    private int offset;

    /**
     * Number of records
     *
     */
    private int total;

    /**
     * Records
     *
     */
    private List<Object> data;

    public PaginationBo(int limit, int offset) {
        this.setLimit(limit);
        this.setOffset(offset);
        this.setTotal(0);
        this.setData(new ArrayList<Object>());
    }

    /**
     * Guarantee this.limit is valid
     *
     * @param limit
     */
    public void setLimit(int limit) {
        this.limit = limit <= 0 ? 1 : limit;
    }

    /**
     * Guarantee this.offset is valid
     *
     * @param offset
     */
    public void setOffset(int offset) {
        this.offset = offset < 0 ? 0 : offset;
    }

    /**
     * Guarantee this.total is valid
     *
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Guarantee this.data is valid
     *
     * @param data
     */
    public void setData(List<Object> data) {
        this.data = data;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getTotal() {
        return this.total;
    }

    public List<Object> getData() {
        return this.data;
    }
}
