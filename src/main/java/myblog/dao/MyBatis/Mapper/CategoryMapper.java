package myblog.dao.MyBatis.Mapper;

import myblog.domain.Category;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {

    /**
     *
     * @param insert
     * @return
     */
    int createCategory(Category insert);

    /**
     *
     * @param categoryId
     * @return
     */
    boolean deleteCategory(int categoryId);

    /**
     *
     * @param update
     * @return
     */
    boolean updateCategory(Category update);

    /**
     *
     * @param categoryId
     * @return
     */
    Category getCategoryById(int categoryId);

    /**
     *
     * @param categoryIds
     * @return
     */
    List<Category> getCategoriesByIds(int[] categoryIds);

    /**
     *
     * @param params
     * @return
     */
    List<Category> getCategoriesByCondition(Map<String, Object> params);
}
