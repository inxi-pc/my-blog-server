package myblog.dao;

import myblog.dao.MyBatis.MyBatisDaoFactory;

/**
 * Dao factory, every dao backend need extend this class
 */
abstract public class DaoFactory {

    /**
     * Backend get method
     *
     * @param backend
     * @return
     */
    public static DaoFactory getDaoFactory(DaoBackend backend) {
        switch (backend) {
            case MYBATIS:
                return MyBatisDaoFactory.getInstance();
            case HIBERNATE:
                return MyBatisDaoFactory.getInstance();
            default:
                return MyBatisDaoFactory.getInstance();
        }
    }

    /**
     * Get post dao
     *
     * @return
     */
    public abstract PostDao getPostDao();

    /**
     * Get user dao
     *
     * @return
     */
    public abstract UserDao getUserDao();

    /**
     * Get comment dao
     *
     * @return
     */
    public abstract CommentDao getCommentDao();

    /**
     * Get category dao
     *
     * @return
     */
    public abstract CategoryDao getCategoryDao();

    /**
     * multi-backend support
     */
    public enum DaoBackend {
        MYBATIS,
        HIBERNATE
    }
}
