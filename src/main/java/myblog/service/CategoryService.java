package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.CategoryDaoMyBatisImpl;
import myblog.domain.Category;
import myblog.domain.Pagination;
import myblog.domain.Sort;
import myblog.exception.DaoException;
import myblog.exception.DomainException;
import myblog.exception.HttpExceptionFactory;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import java.util.HashMap;
import java.util.List;

public class CategoryService {

    public static int createCategory(Category insert) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        if (insert.getCategory_parent_id() != null) {
            Category parent = null;
            try {
                parent = myBatisCategoryDao.getCategoryById(insert.getCategory_parent_id());
            } catch (DaoException e) {
                throw HttpExceptionFactory.produce(BadRequestException.class, e);
            }

            if (parent != null) {
                insert.setCategory_level(parent.getCategory_level() + 1);
                insert.setCategory_root_id(parent.getCategory_root_id());
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.NOT_FOUND,
                        Category.class,
                        HttpExceptionFactory.Reason.NOT_EXIST_CATEGORY_PARENT);
            }
        } else {
            insert.setCategory_level(1);
            insert.setCategory_root_id(null);
            insert.setCategory_parent_id(null);
        }
        insert.setDefaultCategory_enabled();
        insert.setDefaultCategory_created_at();
        insert.setDefaultCategory_updated_at();

        try {
            return myBatisCategoryDao.createCategory(insert);
        } catch (DomainException | DaoException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }
    }

    public static boolean deleteCategory(int categoryId) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        try {
            if (myBatisCategoryDao.getCategoryById(categoryId) != null) {
                return myBatisCategoryDao.deleteCategory(categoryId);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.NOT_FOUND,
                        Category.class,
                        HttpExceptionFactory.Reason.NOT_EXIST);
            }
        } catch (DaoException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }
    }

    public static boolean updateCategory(int categoryId, Category update) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        try {
            if (myBatisCategoryDao.getCategoryById(categoryId) != null) {
                update.setDefaultCategory_updated_at();

                return myBatisCategoryDao.updateCategory(categoryId, update);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.NOT_FOUND,
                        Category.class,
                        HttpExceptionFactory.Reason.NOT_EXIST);
            }
        } catch (DomainException | DaoException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }
    }

    public static Category getCategoryById(int categoryId) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        try {
            return myBatisCategoryDao.getCategoryById(categoryId);
        } catch (DaoException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }
    }

    public static List<Category> getCategories(Category category) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        try {
            HashMap<String, Object> params = category.convertToHashMap(null);

            return myBatisCategoryDao.getCategoriesByCondition(params);
        } catch (DomainException | DaoException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }
    }

    public static Pagination<Category> getCategoryList(Category category, Pagination<Category> page, Sort sort) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        List<Category> categories = null;
        try {
            HashMap<String, Object> params = category.convertToHashMap(null);
            params.put("limit", page.getLimit());
            params.put("offset", page.getOffset());
            params.put("orderBy", sort.getOrder_by());
            params.put("orderType", sort.getOrder_type());

            categories = myBatisCategoryDao.getCategoriesByCondition(params);
        } catch (DaoException | DomainException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }

        page.setData(categories);
        page.setRecordsTotal(myBatisCategoryDao.countAllCategory());

        return page;
    }

    public static List<Category> getCategoriesTree(Category category) {
        List<Category> categories = getCategories(category);

        try {
            return Category.formatCategoryTree(categories);
        } catch (DomainException e) {
            throw HttpExceptionFactory.produce(InternalServerErrorException.class, e);
        }
    }

    public static Pagination<Category> getCategoryListTree(Category category, Pagination<Category> page, Sort sort) {
        getCategoryList(category, page, sort);

        try {
            List<Category> categories = Category.formatCategoryTree(page.getData());
            page.setData(categories);

            return page;
        } catch (DomainException e) {
            throw HttpExceptionFactory.produce(InternalServerErrorException.class, e);
        }
    }
}
