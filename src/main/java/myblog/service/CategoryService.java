package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.CategoryDaoMyBatisImpl;
import myblog.domain.Category;
import myblog.domain.Pagination;
import myblog.domain.Sort;

import java.util.HashMap;
import java.util.List;

public class CategoryService {

    /**
     *
     * @param category
     * @return
     */
    public static int createCategory(Category category) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        return myBatisCategoryDao.createCategory(category);
    }

    /**
     *
     * @param categoryId
     * @return
     */
    public static boolean deleteCategory(int categoryId) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        return myBatisCategoryDao.deleteCategory(categoryId);
    }

    /**
     *
     * @param category
     * @return
     */
    public static boolean updateCategory(Category category) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        return myBatisCategoryDao.updateCategory(category);
    }

    /**
     *
     * @param categoryId
     * @return
     */
    public static Category getCategoryById(int categoryId) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        return myBatisCategoryDao.getCategoryById(categoryId);
    }

    /**
     *
     * @param category
     * @return
     */
    public static List<Category> getCategories(Category category) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();
        HashMap<String, Object> params = category.convertToHashMap();

        return myBatisCategoryDao.getCategoriesByCondition(params);
    }

    /**
     *
     * @param category
     * @param page
     * @param order
     * @return
     */
    public static Pagination<Category> getCategoryList(Category category, Pagination<Category> page, Sort order) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();
        HashMap<String, Object> params = category.convertToHashMap();
        params.put("limit", page.getLimit());
        params.put("offset", page.getOffset());
        params.put("order_by", order.getOrder_by());
        params.put("order_type", order.getOrder_type());

        List<Category> categories = myBatisCategoryDao.getCategoriesByCondition(params);
        page.setData(categories);
        page.setTotal(categories.size());

        return page;
    }
}
