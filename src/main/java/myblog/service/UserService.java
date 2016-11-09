package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.UserDaoMyBatisImpl;
import myblog.domain.User;
import myblog.exception.DaoException;
import myblog.exception.DomainException;
import myblog.exception.HttpExceptionFactory;

import javax.ws.rs.BadRequestException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class UserService {

    public static int registerUser(User register) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        try {
            Map<String, Object> params = register.convertToHashMap(null);
            if (userDao.checkUserIfExist(params) == null) {
                register.setDefaultUser_enabled();
                register.setDefaultUser_created_at();
                register.setDefaultUser_updated_at();

                return userDao.createUser(register);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.CONFLICT,
                        User.class,
                        HttpExceptionFactory.Reason.EXIST_ALREADY);
            }
        } catch (DomainException | DaoException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }
    }

    public static String loginUser(User login) {
        return null;
    }
}
