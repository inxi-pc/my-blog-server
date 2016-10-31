package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.UserDaoMyBatisImpl;
import myblog.domain.User;

public class UserService {

    /**
     *
     * @param register
     * @return
     */
    public static int registerUser(User register) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        register.setDefaultUser_enabled();
        register.setDefaultUser_created_at();
        register.setDefaultUser_updated_at();

        return userDao.createUser(register);
    }

    /**
     *
     * @param login
     * @return
     */
    public static String userLogin(User login) {
        return null;
    }
}
