package myblog.dao;

import myblog.dao.MyBatis.MyBatisDaoFactory;

/**
 * Dao factory, every dao backend need extend this class
 *
 */
abstract public class DaoFactory {

    /**
     * multi-backend support
     */
    public enum DaoBackend {
        MYBATIS,
        HIBERNATE
    }

    /**
     * Get post dao
     *
     * @return
     */
    public abstract PostDao getPostDao();

    /**
     * Backend get method
     *
     * @param backend
     * @return
     * @throws Exception
     */
    public static DaoFactory getDaoFactory(DaoBackend backend) throws Exception {
        switch (backend) {
            case MYBATIS:
                return new MyBatisDaoFactory();
            case HIBERNATE:
                return new MyBatisDaoFactory();
            default:
                throw new Exception();
        }
    }
}
