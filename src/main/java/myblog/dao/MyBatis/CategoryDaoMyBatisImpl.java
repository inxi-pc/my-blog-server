package myblog.dao.MyBatis;

import myblog.dao.CategoryDao;
import myblog.dao.MyBatis.Mapper.CategoryMapper;
import myblog.domain.Category;
import myblog.exception.DaoException;
import org.apache.ibatis.session.SqlSession;

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

    @Override
    public int createCategory(Category insert) {
        if (insert == null) {
            throw new DaoException(DaoException.Type.INSERT_NULL_OBJECT);
        }

        insert.setDefaultableFieldValue();
        insert.checkFieldInsertable();

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
        categoryMapper.createCategory(insert);

        return insert.getCategory_id();
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        if (!Category.isValidCategoryId(categoryId)) {
            throw new DaoException(DaoException.Type.INVALID_DELETED_ID);
        }

        Category delete = new Category();
        delete.setCategory_enabled(false);
        delete.setDefaultCategory_updated_at();

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

        return categoryMapper.deleteCategory(categoryId, delete);
    }

    @Override
    public boolean updateCategory(int categoryId, Category update) {
        if (update == null) {
            return true;
        }

        if (!Category.isValidCategoryId(categoryId)) {
            throw new DaoException(DaoException.Type.INVALID_UPDATED_ID);
        }

        update.checkFieldUpdatable();

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

        return categoryMapper.updateCategory(categoryId, update);
    }

    @Override
    public Category getCategoryById(int categoryId) {
        if (!Category.isValidCategoryId(categoryId)) {
            throw new DaoException(DaoException.Type.INVALID_QUERY_ID);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

        return categoryMapper.getCategoryById(categoryId);
    }

    @Override
    public List<Category> getCategoriesByIds(int[] categoryIds) {
        if (categoryIds == null) {
            throw new DaoException(DaoException.Type.NULL_QUERY_PARAM);
        }

        if (categoryIds.length <= 0) {
            throw new DaoException(DaoException.Type.EMPTY_QUERY_PARAM);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

        return categoryMapper.getCategoriesByIds(categoryIds);
    }

    @Override
    public List<Category> getCategoriesByCondition(Map<String, Object> params) {
        if (params == null) {
            throw new DaoException(DaoException.Type.NULL_QUERY_PARAM);
        }

        if (params.size() <= 0) {
            throw new DaoException(DaoException.Type.EMPTY_QUERY_PARAM);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

        return categoryMapper.getCategoriesByCondition(params);
    }

    @Override
    public int countAllCategory() {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

        return categoryMapper.countAllCategory();
    }
}
