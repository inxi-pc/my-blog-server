package myblog.dao;

import myblog.domain.Category;
import myblog.exception.DaoException;
import myblog.exception.DomainException;

import java.util.List;
import java.util.Map;

public interface CategoryDao {

    /**
     *
     * @param insert
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    int createCategory(Category insert) throws DomainException, DaoException;

    /**
     *
     * @param categoryId
     * @return
     * @throws DaoException
     */
    boolean deleteCategory(int categoryId) throws DaoException;

    /**
     *
     * @param categoryId
     * @param update
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    boolean updateCategory(int categoryId, Category update) throws DomainException, DaoException;

    /**
     *
     * @param categoryId
     * @return
     * @throws DaoException
     */
    Category getCategoryById(int categoryId) throws DaoException;

    /**
     *
     * @param categoryIds
     * @return
     * @throws DaoException
     */
    List<Category> getCategoriesByIds(int[] categoryIds) throws DaoException;

    /**
     *
     * @param params
     * @return
     * @throws DaoException
     */
    List<Category> getCategoriesByCondition(Map<String, Object> params) throws DaoException;

    /**
     *
     * @return
     */
    int countAllCategory();
}
