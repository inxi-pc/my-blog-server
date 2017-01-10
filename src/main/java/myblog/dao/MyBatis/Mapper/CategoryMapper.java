package myblog.dao.MyBatis.Mapper;

import myblog.domain.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {

    /**
     * @param insert
     * @return
     */
    int createCategory(Category insert);

    /**
     * @param categoryId
     * @param delete
     * @return
     */
    boolean deleteCategory(@Param("categoryId") int categoryId, @Param("delete") Category delete);

    /**
     * @param categoryId
     * @param update
     * @return
     */
    boolean updateCategory(@Param("categoryId") int categoryId, @Param("update") Category update);

    /**
     * @param categoryId
     * @return
     */
    Category getCategoryById(int categoryId);

    /**
     * @param categoryIds
     * @return
     */
    List<Category> getCategoriesByIds(int[] categoryIds);

    /**
     * @param params
     * @return
     */
    List<Category> getCategoriesByCondition(Map<String, Object> params);

	/**
     *
     * @param params
     * @return
     */
    int countCategoriesByCondition(Map<String, Object> params);
}
