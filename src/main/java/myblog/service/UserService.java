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
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    /**
     *
     * @param register
     * @return
     */
    public static int registerUser(User register) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        try {
            if (userDao.getUserByCredential(register) == null) {
                register.setDefaultUser_enabled();
                register.setDefaultUser_created_at();
                register.setDefaultUser_updated_at();
                register.encryptPassword();

                return userDao.createUser(register);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.CONFLICT,
                        User.class,
                        HttpExceptionFactory.Reason.EXIST_ALREADY);
            }
        } catch (DomainException | DaoException e) {
            throw HttpExceptionFactory.produce(InternalServerErrorException.class, e);
        }
    }

    /**
     *
     * @param login
     * @return
     */
    public static String loginUser(User login) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        try {
            User user = userDao.getUserByCredential(login);

            if (user == null || user.validPassword(login.getUser_password())) {
                throw HttpExceptionFactory.produce(
                        WebApplicationException.class,
                        Response.Status.UNAUTHORIZED,
                        HttpExceptionFactory.Type.AUTHENTICATE_FAILED,
                        HttpExceptionFactory.Reason.INVALID_USERNAME_OR_PASSWORD);
            }

            Map<String, Object> header = new HashMap<String, Object>();
            header.put("typ", "JWT");

            return Jwts.builder().setHeader(header)
                    .setClaims(user.convertToHashMap(null))
                    .setExpiration(App.getJwtExpiredTime())
                    .signWith(SignatureAlgorithm.HS256, App.getJwtKey()).compact();
        } catch (DomainException | DaoException e) {
            throw HttpExceptionFactory.produce(InternalServerErrorException.class, e);
        }
    }
}
