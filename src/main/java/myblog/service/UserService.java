package myblog.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import myblog.App;
import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.UserDaoMyBatisImpl;
import myblog.domain.Credential;
import myblog.domain.User;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

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
     * @param credential
     * @return
     */
    public static String loginUser(Credential credential) {
        UserDaoMyBatisImpl userDao = (UserDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getUserDao();

        credential.encryptPassword();
        User user = userDao.getUserByCredential(credential);

        if (user != null) {
            Map<String, Object> userMap = user.convertToHashMap();
            Claims claims = new DefaultClaims(userMap);

            return Jwts.builder()
                    .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                    .setClaims(claims)
                    .signWith(SignatureAlgorithm.HS256, App.getJwtKey())
                    .compact();
        } else {
            return null;
        }
    }
}
