package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.UserDaoMyBatisImpl;
import myblog.domain.User;
import myblog.exception.DaoException;
import myblog.exception.DomainException;

import java.util.Map;

public class UserService {

    /**
     *
     * @param register
     * @return
     * @throws DomainException
     * @throws DaoException
     * @throws ServiceException
     */
    public static int registerUser(User register) throws DomainException, DaoException, ServiceException {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        try {
            Map<String, Object> params = register.convertToHashMap(null);
            if (userDao.getUserByCondition(params) == null) {
                register.setDefaultUser_enabled();
                register.setDefaultUser_created_at();
                register.setDefaultUser_updated_at();

                return userDao.createUser(register);
            } else {
                throw new ServiceException(User.class, ServiceException.Type.NOT_FOUND);
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
