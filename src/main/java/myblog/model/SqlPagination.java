package myblog.model;

import java.util.ArrayList;
import java.util.List;

public class SqlPagination {
    private int limit;
    private int offset;
    public int total;
    public List<Object> data;

    public SqlPagination(int limit, int offset) {
        this.setLimit(limit);
        this.setOffset(offset);
        this.total = 0;
        this.data = new ArrayList<Object>();
    }

    public void setLimit(int limit) {
        this.limit = limit <= 0 ? 1 : limit;
    }

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
