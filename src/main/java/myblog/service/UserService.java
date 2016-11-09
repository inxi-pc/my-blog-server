package myblog.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import myblog.App;
import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.UserDaoMyBatisImpl;
import myblog.domain.User;
import myblog.exception.DaoException;
import myblog.exception.DomainException;
import myblog.exception.HttpExceptionFactory;

import javax.ws.rs.BadRequestException;
import java.util.Date;
import java.util.HashMap;
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
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        try {
            User user = userDao.getUserByCredential(login);
            Map<String, Object> header = new HashMap<String, Object>();
            header.put("typ", "JWT");
            return Jwts.builder().setHeader(header)
                    .setClaims(user.convertToHashMap(null))
                    .setExpiration(App.getJwtExpiredTime())
                    .signWith(SignatureAlgorithm.HS256, App.getJwtKey()).compact();
        } catch (DomainException | DaoException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }
    }
}
