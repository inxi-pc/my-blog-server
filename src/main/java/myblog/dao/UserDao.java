package myblog.dao;

import myblog.domain.Credential;
import myblog.domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    /**
     * @param insert
     * @return
     */
    int createUser(User insert);

    /**
     * @param userId
     * @return
     */
    boolean deleteUser(int userId);

    /**
     * @param userId
     * @param update
     * @return
     */
    boolean updateUser(int userId, User update);

    /**
     * @param userId
     * @return
     */
    User getUserById(int userId);

    /**
     * @param credential
     * @return
     */
    User getUserByCredential(Credential credential);

    /**
     * @param userIds
     * @return
     */
    List<User> getUsersByIds(int[] userIds);

    /**
     * @param params
     * @return
     */
    List<User> getUsersByCondition(Map<String, Object> params);

	/**
     *
     * @param params
     * @return
     */
    int countUsersByCondition(Map<String, Object> params);
}
