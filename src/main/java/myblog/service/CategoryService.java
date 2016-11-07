package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.CategoryDaoMyBatisImpl;
import myblog.domain.Category;
import myblog.domain.Pagination;
import myblog.domain.Sort;
import myblog.exception.DaoException;
import myblog.exception.DomainException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
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
                throw new BadRequestException(e.getMessage(), e);
            }

            if (parent != null) {
                insert.setCategory_level(parent.getCategory_level() + 1);
                insert.setCategory_root_id(parent.getCategory_root_id());
            } else {
                throw new BadRequestException("Not found the inserted category's parent");
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
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    public static boolean deleteCategory(int categoryId) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        try {
            if (myBatisCategoryDao.getCategoryById(categoryId) != null) {
                return myBatisCategoryDao.deleteCategory(categoryId);
            } else {
                throw new BadRequestException("Not found the deleted category");
            }
        } catch (DaoException e) {
            throw new BadRequestException(e.getMessage(), e);
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
                throw new BadRequestException("Not found the updated category");
            }
        } catch (DomainException | DaoException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    public static Category getCategoryById(int categoryId) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        try {
            Category category = myBatisCategoryDao.getCategoryById(categoryId);

            if (category != null) {
                return category;
            } else {
                throw new NotFoundException("Not found the category");
            }
        } catch (DaoException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    public static List<Category> getCategories(Category category) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();

        List<Category> categories = null;
        try {
            HashMap<String, Object> params = category.convertToHashMap(null);
            categories = myBatisCategoryDao.getCategoriesByCondition(params);
        } catch (DomainException | DaoException e) {
            throw new BadRequestException(e.getMessage(), e);
        }

        if (categories != null && categories.size() > 0) {
            return categories;
        } else {
            throw new NotFoundException("Not found the categories");
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
            throw new BadRequestException(e.getMessage(), e);
        }

        if (categories != null && categories.size() > 0) {
            page.setData(categories);
            page.setRecordsTotal(myBatisCategoryDao.countAllCategory());
        } else {
            throw new NotFoundException("Not found the category list");
        }

        return page;
    }

    public static List<Category> getCategoriesTree(Category category) {
        List<Category> categories = getCategories(category);

        try {
            return Category.formatCategoryTree(categories);
        } catch (DomainException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }

    public static Pagination<Category> getCategoryListTree(Category category, Pagination<Category> page, Sort sort) {
        getCategoryList(category, page, sort);

        try {
            List<Category> categories = Category.formatCategoryTree(page.getData());
            page.setData(categories);

            return page;
        } catch (DomainException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }
}
