package myblog.dao.MyBatis;

import myblog.dao.DaoFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MyBatisDaoFactory extends DaoFactory {

    /**
     * mybatis session factory instance
     *
     */
    public static SqlSessionFactory defaultSqlSessionFactory;

    /**
     * mybatis-config file name
     *
     */
    private static final String resource = "mybatis-config.xml";

    /**
     *
     *
     */
    static {
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            defaultSqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MyBatisDaoFactory() {}

    // TODO: 8/18/16 use configuration class to new factory
    public static SqlSessionFactory getSqlSessionFactory() {
        return defaultSqlSessionFactory;
    }

    @Override
    public MyBatisPostDao getPostDao() {
        return new MyBatisPostDao();
    }
}
