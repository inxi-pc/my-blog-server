package myblog.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Sql pagination statement
 *
 */
public class Pagination<T> extends Domain {

    /**
     * Pagination length
     */
    private Integer limit;

    /**
     * Start Position of Pagination
     */
    private Integer offset;

    /**
     * Number of records
     *
     */
    private Integer total;

    /**
     * Records
     *
     */
    private List<T> data;

    public Pagination(Integer limit, Integer offset) {
        this.setLimit(limit);
        this.setOffset(offset);
        this.setTotal(0);
        this.setData(new ArrayList<T>());
    }

    /**
     * Guarantee this.limit is valid
     *
     * @param limit
     */
    public void setLimit(Integer limit) {
        if (limit != null && limit >= 0) {
            this.limit = limit;
        } else {
            this.limit = 1;
        }
    }

    /**
     * Guarantee this.offset is valid
     *
     * @param offset
     */
    public void setOffset(Integer offset) {
        if (offset != null && offset >= 0) {
            this.offset = offset;
        } else {
            this.offset = 0;
        }
    }

    /**
     * Guarantee this.total is valid
     *
     * @param total
     */
    public void setTotal(Integer total) {
        if (total != null && total >= 0) {
            this.total = total;
        } else {
            this.total = 0;
        }
    }

    /**
     * Guarantee this.data is valid
     *
     * @param data
     */
    public void setData(List<T> data) {
        if (data != null) {
            this.data = data;
        } else {
            this.data = new ArrayList<T>();
        }
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

    public List<T> getData() {
        return this.data;
    }
}
