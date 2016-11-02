package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.UserMapper;
import myblog.dao.UserDao;
import myblog.domain.Credential;
import myblog.domain.User;
import myblog.exception.FieldNotInsertableException;
import myblog.exception.FieldNotNullableException;
import myblog.exception.FieldNotUpdatableException;
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
            throw new IllegalArgumentException("Unexpected user: Null pointer");
        }

        insert.setDefaultableFieldValue();

        try {
            insert.checkFieldInsertable();
        } catch (FieldNotInsertableException | FieldNotNullableException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
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
     */
    @Override
    public boolean deleteUser(int userId) {
        if (User.isValidUserId(userId)) {
            User user = new User();
            user.setUser_enabled(false);

            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            UserMapper userMapper = session.getMapper(UserMapper.class);

            return userMapper.updateUser(userId, user);
        } else {
            throw new IllegalArgumentException("Unexpected user id: Invalid value");
        }
    }

    /**
     *
     * @param userId
     * @param update
     * @return
     */
    @Override
    public boolean updateUser(int userId, User update) {
        if (update == null) {
            return true;
        }

        try {
            update.checkFieldUpdatable();
        } catch (FieldNotUpdatableException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        UserMapper userMapper = session.getMapper(UserMapper.class);

        return userMapper.updateUser(userId, update);
    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    public User getUserById(int userId) {
        if (User.isValidUserId(userId)) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            UserMapper userMapper = session.getMapper(UserMapper.class);

            return userMapper.getUserById(userId);
        } else {
            throw new IllegalArgumentException("Unexpected user id: Invalid value");
        }
    }

    /**
     *
     * @param credential
     * @return
     */
    @Override
    public User getUserByCredential(Credential credential) {
        if (credential == null) {
            throw new IllegalArgumentException("Unexpected credential: Null pointer");
        }

        if (credential.hasIdentifier() && credential.hasPassword()) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            UserMapper userMapper = session.getMapper(UserMapper.class);

            return userMapper.getUserByCredential(credential);
        } else {
            throw new IllegalArgumentException("Unexpected identifier or password: Absence value");
        }
    }

    /**
     * @param params
     * @return
     */
    @Override
    public User getUserByCondition(Map<String, Object> params) {
        if (params == null) {
            throw new IllegalArgumentException("Unexpected params: Null pointer");
        }

        if (params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            UserMapper userMapper = session.getMapper(UserMapper.class);

            return userMapper.getUserByCondition(params);
        } else {
            throw new IllegalArgumentException("Unexpected params: Empty value");
        }
    }

    /**
     *
     * @param userIds
     * @return
     */
    @Override
    public List<User> getUsersByIds(int[] userIds) {
        if (userIds == null) {
            throw new IllegalArgumentException("Unexpected user ids: Null pointer");
        }

        if (userIds.length > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            UserMapper userMapper = session.getMapper(UserMapper.class);

            return userMapper.getUsersByIds(userIds);
        } else {
            throw new IllegalArgumentException("Unexpected user ids: Empty value");
        }
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    public List<User> getUsersByCondition(Map<String, Object> params) {
        if (params == null) {
            throw new IllegalArgumentException("Unexpected params: Null pointer");
        }

        if (params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            UserMapper userMapper = session.getMapper(UserMapper.class);

            return userMapper.getUsersByCondition(params);
        } else {
            throw new IllegalArgumentException("Unexpected params: Empty value");
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
