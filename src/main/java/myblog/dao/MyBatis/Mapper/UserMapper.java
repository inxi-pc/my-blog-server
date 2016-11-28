package myblog.dao.MyBatis.Mapper;

import myblog.domain.Credential;
import myblog.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    /**
     * @return
     */
    int createUser(User insert);

    /**
     * @return
     */
    boolean deleteUserById(int userId);

    /**
     * @param userId
     * @param update
     * @return
     */
    boolean updateUser(@Param("userId") int userId, @Param("update") User update);

    /**
     * @return
     */
    User getUserById(int userId);

    /**
     * @param credential
     * @return
     */
    User getUserByCredential(@Param("credential") Credential credential);

    /**
     * @return
     */
    List<User> getUsersByIds(int[] userIds);

    /**
     * @param params
     * @return
     */
    List<User> getUsersByCondition(Map<String, Object> params);

    /**
     * @return
     */
    int countAllUser();
}
