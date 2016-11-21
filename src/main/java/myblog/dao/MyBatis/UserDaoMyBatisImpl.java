package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.UserMapper;
import myblog.dao.UserDao;
import myblog.domain.Credential;
import myblog.domain.User;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.exception.LiteralMessageMeta;
import org.apache.ibatis.session.SqlSession;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class UserDaoMyBatisImpl implements UserDao {

    /**
     * Reference of MyBatisDaoFactory instance
     *
     */
    private MyBatisDaoFactory myBatisDaoFactory;

    /**
     * @param factory
     *
     */
    UserDaoMyBatisImpl(MyBatisDaoFactory factory) {
        this.myBatisDaoFactory = factory;
    }

    @Override
    public int createUser(User insert) {
        if (insert == null) {
            throw new GenericException(
                    GenericMessageMeta.NULL_OBJECT_TO_INSERT,
                    User.class, Response.Status.BAD_REQUEST);
        }

        insert.setDefaultableFieldValue();
        insert.checkFieldInsertable();

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);
        userMapper.createUser(insert);

        return insert.getUser_id();
    }

    @Override
    public boolean deleteUser(int userId) {
        if (!User.isValidUserId(userId)) {
            throw new GenericException(
                    GenericMessageMeta.INVALID_ID_TO_DELETE,
                    "user_id", Response.Status.BAD_REQUEST);
        }

        User user = new User();
        user.setUser_enabled(false);

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.updateUser(userId, user);
    }

    @Override
    public boolean updateUser(int userId, User update) {
        if (update == null) {
            return true;
        }

        if (!User.isValidUserId(userId)) {
            throw new GenericException(
                    GenericMessageMeta.INVALID_ID_TO_UPDATE,
                    "user_id", Response.Status.BAD_REQUEST);
        }

        update.checkFieldUpdatable();

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.updateUser(userId, update);
    }

    @Override
    public User getUserById(int userId) {
        if (!User.isValidUserId(userId)) {
            throw new GenericException(
                    GenericMessageMeta.INVALID_ID_TO_QUERY,
                    "user_id", Response.Status.BAD_REQUEST);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.getUserById(userId);
    }

    @Override
    public User getUserByCredential(Credential credential) {
        if (credential == null) {
            throw new GenericException(
                    LiteralMessageMeta.NULL_CREDENTIAL_TO_QUERY,
                    Response.Status.BAD_REQUEST);
        }

        if (!credential.hasIdentifier()) {
            throw new GenericException(
                    LiteralMessageMeta.ILLEGAL_QUANTITY_IDENTIFIER,
                    Response.Status.BAD_REQUEST);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.getUserByCredential(credential);
    }

    @Override
    public List<User> getUsersByIds(int[] userIds) {
        if (userIds == null) {
            throw new GenericException(
                    LiteralMessageMeta.NULL_PARAM_TO_QUERY,
                    Response.Status.BAD_REQUEST);
        }

        if (userIds.length <= 0) {
            throw new GenericException(
                    LiteralMessageMeta.EMPTY_PARAM_TO_QUERY,
                    Response.Status.BAD_REQUEST);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.getUsersByIds(userIds);
    }

    @Override
    public List<User> getUsersByCondition(Map<String, Object> params) {
        if (params == null) {
            throw new GenericException(
                    LiteralMessageMeta.NULL_PARAM_TO_QUERY,
                    Response.Status.BAD_REQUEST);
        }

        if (params.size() <= 0) {
            throw new GenericException(
                    LiteralMessageMeta.EMPTY_PARAM_TO_QUERY, 
                    Response.Status.BAD_REQUEST);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.getUsersByCondition(params);
    }

    @Override
    public int countAllUser() {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.countAllUser();
    }
}
