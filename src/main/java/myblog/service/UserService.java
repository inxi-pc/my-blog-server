package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.UserDaoMyBatisImpl;
import myblog.dao.Sql.Pagination;
import myblog.dao.Sql.Sort;
import myblog.domain.User;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

public class UserService {

    /**
     * @param insert
     * @return
     */
    public static int createUser(User insert) {
        UserDaoMyBatisImpl myBatisUserDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        insert.setDefaultableFieldValue();

        return myBatisUserDao.createUser(insert);
    }

    /**
     * @param userId
     * @return
     */
    public static boolean deleteUser(int userId) {
        UserDaoMyBatisImpl myBatisUserDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        if (myBatisUserDao.getUserById(userId) != null) {
            return myBatisUserDao.deleteUser(userId);
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
        UserDaoMyBatisImpl myBatisUserDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        if (myBatisUserDao.getUserById(userId) != null) {
            update.setDefaultUser_updated_at();

            return myBatisUserDao.updateUser(userId, update);
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, User.class, Response.Status.BAD_REQUEST);
        }
    }

    /**
     * @param userId
     * @return
     */
    public static User getUserById(int userId) {
        UserDaoMyBatisImpl myBatisUserDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        return myBatisUserDao.getUserById(userId);
    }

    /**
     * @param user
     * @return
     */
    public static List<User> getUsers(User user) {
        UserDaoMyBatisImpl myBatisUserDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        HashMap<String, Object> params = user.convertToHashMap(null);

        return myBatisUserDao.getUsersByCondition(params);
    }

    /**
     * @param user
     * @param page
     * @param sort
     * @return
     */
    public static Pagination<User> getUserList(User user, Pagination<User> page, Sort<User> sort) {
        UserDaoMyBatisImpl myBatisUserDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        HashMap<String, Object> params = user.convertToHashMap(null);
        params.put("limit", page.getLimit());
        params.put("offset", page.getOffset());
        params.put("orderBy", sort.getOrder_by());
        params.put("orderType", sort.getOrder_type());
        List<User> users = myBatisUserDao.getUsersByCondition(params);

        page.setData(users);
        page.setRecordsTotal(myBatisUserDao.countAllUser());

        return page;
    }
}
