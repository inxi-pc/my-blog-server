package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.UserDaoMyBatisImpl;
import myblog.domain.User;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;

import javax.ws.rs.core.Response;

public class UserService {

    /**
     * @param insert
     * @return
     */
    public static int createUser(User insert) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        insert.setDefaultableFieldValue();

        return userDao.createUser(insert);
    }

    /**
     * @param userId
     * @return
     */
    public static boolean deleteUser(int userId) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        if (userDao.getUserById(userId) != null) {
            return userDao.deleteUser(userId);
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, User.class, Response.Status.BAD_REQUEST);
        }
    }

    /**
     * @param userId
     * @param update
     * @return
     */
    public static boolean updateUser(int userId, User update) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        if (userDao.getUserById(userId) != null) {
            update.setDefaultUser_updated_at();

            return userDao.updateUser(userId, update);
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, User.class, Response.Status.BAD_REQUEST);
        }
    }

    /**
     * @param userId
     * @return
     */
    public static User getUserById(int userId) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        return userDao.getUserById(userId);
    }
}
