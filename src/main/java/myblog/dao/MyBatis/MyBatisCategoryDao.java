package myblog.dao.MyBatis;

import myblog.dao.CategoryDao;
import myblog.dao.MyBatis.Mapper.CategoryMapper;
import myblog.model.persistence.Category;
import org.apache.ibatis.session.SqlSession;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Map;

public class MyBatisCategoryDao implements CategoryDao {
    /**
     * Reference of MyBatisDaoFactory instance
     */
    private MyBatisDaoFactory myBatisDaoFactory;

    MyBatisCategoryDao(MyBatisDaoFactory factory) {
        this.myBatisDaoFactory = factory;
    }

    public List<Category> getCategoriesByCondition(Map<String, Object> params) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        CategoryMapper categoryMapper = session.getMapper(CategoryMapper.class);
        List<Category> categories = categoryMapper.getCategoriesByCondition(params);
        session.commit();
        session.close();

        if (categories == null || categories.isEmpty()) {
            throw new NotFoundException("Categories not found");
        } else {
            return categories;
        }
    }
}
