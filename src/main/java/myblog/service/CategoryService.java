package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.CategoryDaoMyBatisImpl;
import myblog.domain.Category;
import myblog.domain.Pagination;
import myblog.domain.Sort;

import javax.ws.rs.NotFoundException;
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

        if (category.getCategory_parent_id() != null) {
            Category parent = myBatisCategoryDao.getCategoryById(category.getCategory_parent_id());
            if (parent != null) {
                category.setCategory_level(parent.getCategory_level() + 1);
                category.setCategory_root_id(parent.getCategory_root_id());
            } else {
                throw new NotFoundException("Not found parent category: Id = " + category.getCategory_parent_id());
            }
        } else {
            category.setCategory_level(1);
            category.setCategory_root_id(null);
            category.setCategory_parent_id(null);
        }
        category.setDefaultCategory_enabled();
        category.setDefaultCategory_created_at();
        category.setDefaultCategory_updated_at();

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

        if (myBatisCategoryDao.getCategoryById(categoryId) != null) {
            return myBatisCategoryDao.deleteCategory(categoryId);
        } else {
            throw new NotFoundException("Not found category: Id = " + categoryId);
        }
    }

    /**
     *
     * @param categoryId
     * @param category
     * @return
     */
    public static boolean updateCategory(int categoryId, Category category)  {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        if (myBatisCategoryDao.getCategoryById(categoryId) != null) {
            category.setDefaultCategory_updated_at();

            return myBatisCategoryDao.updateCategory(categoryId, category);
        } else {
            throw new NotFoundException("Not found category: Id = " + categoryId);
        }
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
     * @param sort
     * @return
     */
    public static Pagination<Category> getCategoryList(Category category, Pagination<Category> page, Sort sort) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        HashMap<String, Object> params = category.convertToHashMap();
        params.put("limit", page.getLimit());
        params.put("offset", page.getOffset());
        params.put("orderBy", sort.getOrder_by());
        params.put("orderType", sort.getOrder_type());

        List<Category> categories = myBatisCategoryDao.getCategoriesByCondition(params);
        page.setData(categories);
        page.setRecordsTotal(myBatisCategoryDao.countAllCategory());

        return page;
    }

    /**
     *
     * @param category
     * @return
     */
    public static List<Category> getCategoriesTree(Category category) {
        List<Category> categories = getCategories(category);
        categories = Category.formatCategoryTree(categories);

        return categories;
    }
    /**
     *
     * @param category
     * @param page
     * @param sort
     * @return
     */
    public static Pagination<Category> getCategoryListTree(Category category, Pagination<Category> page, Sort sort) {
        getCategoryList(category, page, sort);
        page.setData(Category.formatCategoryTree(page.getData()));

        return page;
    }
}
