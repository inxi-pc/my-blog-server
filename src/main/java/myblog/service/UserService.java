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

public class UserService {

    /**
     * Register a user
     *
     * @param register an {@link User}
     * @return return the created user id
     */
    public static int registerUser(User register) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        if (userDao.getUserByCredential(register) == null) {
            register.setDefaultUser_enabled();
            register.setDefaultUser_created_at();
            register.setDefaultUser_updated_at();
            register.encryptPassword();

            return userDao.createUser(register);
        } else {
            throw new GenericException(GenericMessageMeta.EXISTED_OBJECT, User.class, Response.Status.BAD_REQUEST);
        }
    }

    /**
     * Login by user object provided information
     *
     * @param login an {@link User}
     * @return return the success token and user object
     */
    public static Map<String, Object> loginUser(User login) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        User user = userDao.getUserByCredential(login);
        if (user == null) {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, User.class, Response.Status.UNAUTHORIZED);
        }
        if (!user.validatePassword(login.getPassword())) {
            throw new GenericException(LiteralMessageMeta.INCORRECT_PASSWORD, Response.Status.UNAUTHORIZED);
        }

        Map<String, Object> header = new HashMap<String, Object>();
        header.put("typ", "JWT");
        String token = Jwts.builder().setHeader(header)
                .setClaims(user.convertToHashMap(null))
                .setExpiration(App.getJwtExpiredTime())
                .signWith(SignatureAlgorithm.HS256, App.getJwtKey()).compact();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("token", token);
        result.put("user", user);

        return result;
    }
}
