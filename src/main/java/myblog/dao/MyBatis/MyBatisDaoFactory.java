package myblog.dao.MyBatis;

import myblog.dao.DaoFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisDaoFactory extends DaoFactory {

    /**
     * mybatis-config file name
     */
    private static final String resource = "mybatis-config.xml";
    private static MyBatisDaoFactory instance = new MyBatisDaoFactory();
    /**
     * mybatis session factory instance
     */
    private SqlSessionFactory defaultSqlSessionFactory;

    private MyBatisDaoFactory() {
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            this.defaultSqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("MyBatis dao factory instance initial failed: " + e.getMessage(), e);
        }
    }

    public static MyBatisDaoFactory getInstance() {
        if (instance != null) {
            return instance;
        } else {
            throw new NullPointerException("MyBatis dao factory instance is null");
        }
    }

    /**
     * @return
     */
    // TODO: 8/18/16 use configuration class to new factory
    public static SqlSessionFactory getSqlSessionFactory() {
        return null;
    }

    /**
     * @return
     */
    public SqlSessionFactory getDefaultSqlSessionFactory() {
        return this.defaultSqlSessionFactory;
    }

    @Override
    public PostDaoMyBatisImpl getPostDao() {
        return new PostDaoMyBatisImpl(this);
    }

    @Override
    public CommentDaoMyBatisImpl getCommentDao() {
        return new CommentDaoMyBatisImpl();
    }

    @Override
    public UserDaoMyBatisImpl getUserDao() {
        return new UserDaoMyBatisImpl(this);
    }

    @Override
    public CategoryDaoMyBatisImpl getCategoryDao() {
        return new CategoryDaoMyBatisImpl(this);
    }
}
