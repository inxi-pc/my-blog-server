package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.UserMapper;
import myblog.dao.UserDao;
import myblog.domain.Credential;
import myblog.domain.User;
import myblog.exception.DaoException;
import myblog.exception.DomainException;
import org.apache.ibatis.session.SqlSession;

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
            throw new DaoException(DaoException.Type.INSERT_NULL_OBJECT);
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
            throw new DaoException(DaoException.Type.INVALID_DELETED_ID);
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
            throw new DaoException(DaoException.Type.INVALID_UPDATED_ID);
        }

        update.checkFieldUpdatable();

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.updateUser(userId, update);
    }

    @Override
    public User getUserById(int userId) {
        if (!User.isValidUserId(userId)) {
            throw new DaoException(DaoException.Type.INVALID_QUERY_ID);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.getUserById(userId);
    }

    @Override
    public User getUserByCredential(Credential credential) {
        if (credential == null) {
            throw new DaoException(DaoException.Type.NULL_QUERY_PARAM);
        }

        if (!credential.hasIdentifier()) {
            throw new DomainException(DomainException.Type.ILLEGAL_NUMBER_OF_IDENTIFIER);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.getUserByCredential(credential);
    }

    @Override
    public List<User> getUsersByIds(int[] userIds) {
        if (userIds == null) {
            throw new DaoException(DaoException.Type.NULL_QUERY_PARAM);
        }

        if (userIds.length <= 0) {
            throw new DaoException(DaoException.Type.EMPTY_QUERY_PARAM);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.getUsersByIds(userIds);
    }

    @Override
    public List<User> getUsersByCondition(Map<String, Object> params) throws DaoException {
        if (params == null) {
            throw new DaoException(DaoException.Type.NULL_QUERY_PARAM);
        }

        if (params.size() <= 0) {
            throw new DaoException(DaoException.Type.EMPTY_QUERY_PARAM);
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
