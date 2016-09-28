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
    public MyBatisPostDao getPostDao() {
        return new MyBatisPostDao(this);
    }

    @Override
    public MyBatisCommentDao getCommentDao() {
        return new MyBatisCommentDao();
    }

    @Override
    public MyBatisUserDao getUserDao() {
        return new MyBatisUserDao();
    }

    @Override
    public MyBatisCategoryDao getCategoryDao() {
        return new MyBatisCategoryDao(this);
    }
}
