package myblog.model.persistent;

import java.util.ArrayList;
import java.util.List;

/**
 * Sql pagination statement
 *
 */
public class SqlPagination {

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
    public int total;

    /**
     * Records
     *
     */
    public List<Object> data;

    public SqlPagination(int limit, int offset) {
        this.setLimit(limit);
        this.setOffset(offset);
        this.total = 0;
        this.data = new ArrayList<Object>();
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

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }
}
