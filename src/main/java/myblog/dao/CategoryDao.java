package myblog.dao;

import myblog.model.persistence.Category;

import java.util.List;
import java.util.Map;

public interface CategoryDao {
    /**
     *
     * @param params
     * @return
     */
    List<Category> getCategoriesByCondition(Map<String, Object> params);
}
