package myblog.service;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import myblog.App;
import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.UserDaoMyBatisImpl;
import myblog.domain.Credential;
import myblog.domain.User;

import javax.ws.rs.ForbiddenException;
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

        Map<String, Object> params;

        try {
            params = register.convertToHashMap();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }

        if (userDao.getUserByCondition(params) == null) {
            register.setDefaultUser_enabled();
            register.setDefaultUser_created_at();
            register.setDefaultUser_updated_at();

            return userDao.createUser(register);
        } else {
            throw new ForbiddenException("Unexpected register: Already exist");
        }
    }

    /**
     *
     * @param credential
     * @return
     */
    public static String loginUser(Credential credential) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        credential.encryptPassword();

        User user = userDao.getUserByCredential(credential);
        if (user != null) {
            Map<String, Object> claims;
            try {
                claims = user.convertToHashMap();
            } catch (Exception e) {
                throw new IllegalArgumentException(e);
            }

            String token = Jwts.builder()
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, App.getJwtKey())
                    .compact();

            return token;
        } else {

        }
    }
}
