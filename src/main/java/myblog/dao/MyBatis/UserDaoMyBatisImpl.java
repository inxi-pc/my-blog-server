package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.UserMapper;
import myblog.dao.UserDao;
import myblog.domain.Credential;
import myblog.domain.User;
import myblog.exception.DaoException;
import myblog.exception.DomainException;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Array;
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

    /**
     *
     * @param insert
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    @Override
    public int createUser(User insert) throws DomainException, DaoException {
        if (insert == null) {
            throw new DaoException(User.class, DaoException.Type.NULL_POINTER);
        }

        try {
            insert.setDefaultableFieldValue();
            insert.checkFieldInsertable();
        } catch (DomainException e) {
            throw e;
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);
        userMapper.createUser(insert);

        return insert.getUser_id();
    }

    /**
     *
     * @param userId
     * @return
     * @throws DaoException
     */
    @Override
    public boolean deleteUser(int userId) throws DaoException {
        if (User.isValidUserId(userId)) {
            User user = new User();
            user.setUser_enabled(false);

            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            UserMapper userMapper = session.getMapper(UserMapper.class);

            return userMapper.updateUser(userId, user);
        } else {
            throw new DaoException(Integer.class, DaoException.Type.INVALID_PARAM);
        }
    }

    /**
     *
     * @param userId
     * @param update
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    @Override
    public boolean updateUser(int userId, User update) throws DomainException, DaoException {
        if (update == null) {
            return true;
        }

        if (!User.isValidUserId(userId)) {
            throw new DaoException(Integer.class, DaoException.Type.INVALID_PARAM);
        }

        try {
            update.checkFieldUpdatable();
        } catch (DomainException e) {
            throw e;
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.updateUser(userId, update);
    }

    /**
     *
     * @param userId
     * @return
     * @throws DaoException
     */
    @Override
    public User getUserById(int userId) throws DaoException {
        if (User.isValidUserId(userId)) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            UserMapper userMapper = session.getMapper(UserMapper.class);

            return userMapper.getUserById(userId);
        } else {
            throw new DaoException(Integer.class, DaoException.Type.INVALID_PARAM);
        }
    }

    /**
     *
     * @param credential
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    @Override
    public User getUserByCredential(Credential credential) throws DomainException, DaoException {
        if (credential == null) {
            throw new DaoException(Credential.class, DaoException.Type.NULL_POINTER);
        }
        if (!credential.hasIdentifier()) {
            throw new DomainException(DomainException.Type.ILLEGAL_NUMBER_OF_IDENTIFIER);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.getUserByCredential(credential);
    }

    /**
     *
     * @param userIds
     * @return
     * @throws DaoException
     */
    @Override
    public List<User> getUsersByIds(int[] userIds) throws DaoException {
        if (userIds == null) {
            throw new DaoException(Array.class, DaoException.Type.NULL_POINTER);
        }

        if (userIds.length > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            UserMapper userMapper = session.getMapper(UserMapper.class);

            return userMapper.getUsersByIds(userIds);
        } else {
            throw new DaoException(Array.class, DaoException.Type.EMPTY_VALUE);
        }
    }

    /**
     *
     * @param params
     * @return
     * @throws DaoException
     */
    @Override
    public List<User> getUsersByCondition(Map<String, Object> params) throws DaoException {
        if (params == null) {
            throw new DaoException(Map.class, DaoException.Type.NULL_POINTER);
        }

        if (params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            UserMapper userMapper = session.getMapper(UserMapper.class);

            return userMapper.getUsersByCondition(params);
        } else {
            throw new DaoException(Map.class, DaoException.Type.EMPTY_VALUE);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int countAllUser() {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.countAllUser();
    }
}
