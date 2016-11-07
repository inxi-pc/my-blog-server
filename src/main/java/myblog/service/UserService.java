package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.UserDaoMyBatisImpl;
import myblog.domain.User;
import myblog.exception.DaoException;
import myblog.exception.DomainException;

import javax.ws.rs.BadRequestException;
import java.util.Map;

public class UserService {

    public static int registerUser(User register) {
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
                throw new BadRequestException("Registered user has been exist");
            }
        } catch (DomainException | DaoException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    public static String loginUser(User login) {
        return null;
    }
}
