package myblog.dao;

import myblog.domain.Credential;
import myblog.domain.User;
import myblog.exception.DaoException;
import myblog.exception.DomainException;

import java.util.List;
import java.util.Map;

public interface UserDao {

    /**
     *
     * @param insert
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    int createUser(User insert) throws DomainException, DaoException;

    /**
     *
     * @param userId
     * @return
     * @throws DaoException
     */
    boolean deleteUser(int userId) throws DaoException;

    /**
     *
     * @param userId
     * @param update
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    boolean updateUser(int userId, User update) throws DomainException, DaoException;

    /**
     *
     * @param userId
     * @return
     * @throws DaoException
     */
    User getUserById(int userId) throws DaoException;

    /**
     *
     * @param credential
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    User getUserByCredential(Credential credential) throws DomainException, DaoException;

    /**
     *
     * @param userIds
     * @return
     * @throws DaoException
     */
    List<User> getUsersByIds(int[] userIds) throws DaoException;

    /**
     *
     * @param params
     * @return
     * @throws DaoException
     */
    List<User> getUsersByCondition(Map<String, Object> params) throws DaoException;

    /**
     *
     * @return
     */
    int countAllUser();
}
