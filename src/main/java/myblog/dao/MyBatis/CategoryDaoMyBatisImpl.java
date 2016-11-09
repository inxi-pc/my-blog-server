package myblog.dao.MyBatis;

import myblog.dao.CategoryDao;
import myblog.dao.MyBatis.Mapper.CategoryMapper;
import myblog.domain.Category;
import myblog.exception.DaoException;
import myblog.exception.DomainException;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

public class CategoryDaoMyBatisImpl implements CategoryDao {

    /**
     * Reference of MyBatisDaoFactory instance
     *
     */
    private MyBatisDaoFactory myBatisDaoFactory;

    /**
     *
     * @param factory
     */
    CategoryDaoMyBatisImpl(MyBatisDaoFactory factory) {
        this.myBatisDaoFactory = factory;
    }

    /**
     *
     * @param insert
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    @Override
    public int createCategory(Category insert) throws DomainException, DaoException {
        if (insert == null) {
            throw new DaoException(Category.class, DaoException.Type.NULL_POINTER);
        }

        try {
            insert.setDefaultableFieldValue();
            insert.checkFieldInsertable();
        } catch (DomainException e) {
            throw e;
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
        categoryMapper.createCategory(insert);

        return insert.getCategory_id();
    }

    /**
     *
     * @param categoryId
     * @return
     * @throws DaoException
     */
    @Override
    public boolean deleteCategory(int categoryId) throws DaoException {
        if (Category.isValidCategoryId(categoryId)) {
            Category delete = new Category();
            delete.setCategory_enabled(false);
            delete.setDefaultCategory_updated_at();

            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.deleteCategory(categoryId, delete);
        } else {
            throw new DaoException(Integer.class, DaoException.Type.INVALID_PARAM);
        }
    }

    /**
     *
     * @param categoryId
     * @param update
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    @Override
    public boolean updateCategory(int categoryId, Category update) throws DomainException, DaoException {
        if (update == null) {
            return true;
        }

        if (!Category.isValidCategoryId(categoryId)) {
            throw new DaoException(Integer.class, DaoException.Type.INVALID_PARAM);
        }

        try {
            update.checkFieldUpdatable();
        } catch (DomainException e) {
            throw e;
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

        return categoryMapper.updateCategory(categoryId, update);
    }

    /**
     *
     * @param categoryId
     * @return
     * @throws DaoException
     */
    @Override
    public Category getCategoryById(int categoryId) throws DaoException {
        if (Category.isValidCategoryId(categoryId)) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.getCategoryById(categoryId);
        } else {
            throw new DaoException(Integer.class, DaoException.Type.INVALID_PARAM);
        }
    }

    /**
     *
     * @param categoryIds
     * @return
     * @throws DaoException
     */
    @Override
    public List<Category> getCategoriesByIds(int[] categoryIds) throws DaoException {
        if (categoryIds == null) {
            throw new DaoException(Array.class, DaoException.Type.NULL_POINTER);
        }

        if (categoryIds.length > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.getCategoriesByIds(categoryIds);
        } else {
            throw new DaoException(Array.class, DaoException.Type.EMPTY_VALUE);
        }
    }

    /**
     *
     * @param params
     * @return
     * @throws DaoException
     */
    @Override
    public List<Category> getCategoriesByCondition(Map<String, Object> params) throws DaoException {
        if (params == null) {
            throw new DaoException(Map.class, DaoException.Type.NULL_POINTER);
        }

        if (params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.getCategoriesByCondition(params);
        } else {
            throw new DaoException(Map.class, DaoException.Type.EMPTY_VALUE);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int countAllCategory() {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

        return categoryMapper.countAllCategory();
    }
}
