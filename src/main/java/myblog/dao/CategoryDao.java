package myblog.dao;

import myblog.model.Category;

import java.util.List;
import java.util.Map;

public interface CategoryDao {
    /**
     *
     * @param params
     * @return
     */
    List<Category> getCategoryListByCondition(Map<String, Object> params);
}
