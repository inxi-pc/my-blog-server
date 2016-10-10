package myblog.dao.MyBatis;

import myblog.dao.DaoFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.ws.rs.InternalServerErrorException;
import java.io.IOException;
import java.io.InputStream;

public class MyBatisDaoFactory extends DaoFactory {

    private static MyBatisDaoFactory instance = new MyBatisDaoFactory();
    /**
     * mybatis session factory instance
     *
     */
    private SqlSessionFactory defaultSqlSessionFactory;

    /**
     * mybatis-config file name
     *
     */
    private static final String resource = "myblog/mybatis-config.xml";

    private MyBatisDaoFactory() {
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            this.defaultSqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            throw new InternalServerErrorException(e);
        }
    }

    public static MyBatisDaoFactory getInstance() {
        return instance;
    }

    /**
     *
     * @return
     */
    // TODO: 8/18/16 use configuration class to new factory
    public static SqlSessionFactory getSqlSessionFactory() {
        return null;
    }

    /**
     *
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
        return new UserDaoMyBatisImpl();
    }

    @Override
    public CategoryDaoMyBatisImpl getCategoryDao() {
        return new CategoryDaoMyBatisImpl(this);
    }
}
