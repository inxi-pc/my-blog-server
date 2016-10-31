package myblog.dao.MyBatis;

import myblog.dao.CategoryDao;
import myblog.dao.MyBatis.Mapper.CategoryMapper;
import myblog.domain.Category;
import myblog.exception.FieldNotInsertableException;
import myblog.exception.FieldNotNullableException;
import myblog.exception.FieldNotUpdatableException;
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

    /**
     *
     * @param insert
     * @return
     */
    @Override
    public int createCategory(Category insert) {
        if (insert == null) {
            throw new IllegalArgumentException("Unexpected category: " + "Null pointer");
        }

        insert.setDefaultableFieldValue();

        try {
            insert.checkFieldInsertable();
        } catch (FieldNotInsertableException | FieldNotNullableException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
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
     */
    @Override
    public boolean deleteCategory(int categoryId) {
        if (Category.isValidCategoryId(categoryId)) {
            Category delete = new Category();
            delete.setCategory_enabled(false);
            delete.setDefaultCategory_updated_at();

            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.deleteCategory(categoryId, delete);
        } else {
            throw new IllegalArgumentException("Unexpected category id: Invalid value");
        }
    }

    /**
     *
     * @param categoryId
     * @param update
     * @return
     */
    @Override
    public boolean updateCategory(int categoryId, Category update) {
        if (update == null) {
            throw new IllegalArgumentException("Unexpected category: " + "Null pointer");
        }

        try {
            update.checkFieldUpdatable();
        } catch (FieldNotUpdatableException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

        return categoryMapper.updateCategory(categoryId, update);
    }

    /**
     *
     * @param categoryId
     * @return
     */
    @Override
    public Category getCategoryById(int categoryId) {
        if (Category.isValidCategoryId(categoryId)) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.getCategoryById(categoryId);
        } else {
            throw new IllegalArgumentException("Unexpected category id: Invalid value");
        }
    }

    /**
     *
     * @param categoryIds
     * @return
     */
    @Override
    public List<Category> getCategoriesByIds(int[] categoryIds) {
        if (categoryIds == null) {
            throw new IllegalArgumentException("Unexpected category ids: " + "Null pointer");
        }

        if (categoryIds.length > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.getCategoriesByIds(categoryIds);
        } else {
            throw new IllegalArgumentException("Unexpected category ids: Invalid value");
        }
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    public List<Category> getCategoriesByCondition(Map<String, Object> params) {
        if (params == null) {
            throw new IllegalArgumentException("Unexpected params: " + "Null pointer");
        }

        if (params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.getCategoriesByCondition(params);
        } else {
            throw new IllegalArgumentException("Unexpected params: Empty value");
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
