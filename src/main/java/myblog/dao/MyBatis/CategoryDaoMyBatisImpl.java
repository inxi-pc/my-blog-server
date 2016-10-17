package myblog.dao.MyBatis;

import myblog.dao.CategoryDao;
import myblog.dao.MyBatis.Mapper.CategoryMapper;
import myblog.domain.Category;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class CategoryDaoMyBatisImpl implements CategoryDao {

    /**
     * Reference of MyBatisDaoFactory instance
     *
     */
    private MyBatisDaoFactory myBatisDaoFactory;

    CategoryDaoMyBatisImpl(MyBatisDaoFactory factory) {
        this.myBatisDaoFactory = factory;
    }

    /**
     *
     * @param insert
     * @return
     */
    public int createCategory(Category insert) {
        if (insert != null && insert.checkFieldsConstraint()) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
            categoryMapper.createCategory(insert);

            return insert.getCategory_id();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param categoryId
     * @return
     */
    public boolean deleteCategory(int categoryId) {
        if (Category.isValidCategoryId(categoryId)) {
            Category category = new Category();
            category.setCategory_id(categoryId);
            category.setCategory_enabled(false);

            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.updateCategory(category);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param update
     * @return
     */
    public boolean updateCategory(Category update) {
        if (update != null && !update.checkAllFieldsIsNullExceptPK()) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.updateCategory(update);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param categoryId
     * @return
     */
    public Category getCategoryById(int categoryId) {
        if (Category.isValidCategoryId(categoryId)) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.getCategoryById(categoryId);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param categoryIds
     * @return
     */
    public List<Category> getCategoriesByIds(int[] categoryIds) {
        if (categoryIds != null && categoryIds.length > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.getCategoriesByIds(categoryIds);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param params
     * @return
     */
    public List<Category> getCategoriesByCondition(Map<String, Object> params) {
        if (params != null && params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);

            return categoryMapper.getCategoriesByCondition(params);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
