package myblog.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import myblog.App;
import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.UserDaoMyBatisImpl;
import myblog.domain.User;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.exception.LiteralMessageMeta;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    /**
     * Register a user
     *
     * @param register an {@link User}
     * @return return the created user id
     */
    public static int register(User register) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        if (userDao.getUserByCredential(register) == null) {
            register.setDefaultUser_created_at();
            register.setDefaultUser_updated_at();
            register.setDefaultUser_enabled();

            return userDao.createUser(register);
        } else {
            throw new GenericException(GenericMessageMeta.EXISTED_OBJECT, User.class, Response.Status.BAD_REQUEST);
        }
    }

    /**
     * Generate a new token by user
     *
     * @param token an {@link User}
     * @return return the token
     */
    private static String refreshToken(User token) {
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("typ", "JWT");
        User tokenUser = new User();
        tokenUser.setUser_id(token.getUser_id());

        return Jwts.builder().setHeader(header)
                .setClaims(tokenUser.convertToHashMap(null))
                .setExpiration(App.getJwtExpiredTime())
                .signWith(SignatureAlgorithm.HS256, App.getJwtKey()).compact();
    }

    /**
     * Login by user object provided information
     *
     * @param login an {@link User}
     * @return return the success token and user object
     */
    public static Map<String, Object> login(User login) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        User user = userDao.getUserByCredential(login);
        if (user == null) {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, User.class, Response.Status.UNAUTHORIZED);
        }
        if (!user.validatePassword(login.getPassword())) {
            throw new GenericException(LiteralMessageMeta.INCORRECT_PASSWORD, Response.Status.UNAUTHORIZED);
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("token", refreshToken(user));
        result.put("user", user);

        return result;
    }

    /**
     * @param userId
     * @return
     */
    public static Map<String, Object> ping(int userId) {
        User user = new User();
        user.setUser_id(userId);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("token", refreshToken(user));

        return result;
    }
}
