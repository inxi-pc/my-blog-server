package myblog.dao.MyBatis.Mapper;

import myblog.dao.Sql.Condition;
import myblog.domain.Post;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PostMapper {
    /**
     * @return
     */
    int createPost(Post insert);

    /**
     * @return
     */
    boolean deletePostById(int postId);

    /**
     * @param postId
     * @param update
     * @return
     */
    boolean updatePost(@Param("postId") int postId, @Param("update") Post update);

	/**
     *
     * @param postId
     * @param condition
     * @return
     */
    Post getPostById(@Param("postId") int postId, @Param("condition") Condition condition);

    /**
     *
     * @param postIds
     * @param condition
     * @return
     */
    List<Post> getPostsByIds(@Param("postIds") int[] postIds, @Param("condition") Condition condition);

    /**
     *
     * @param params
     * @param condition
     * @return
     */
    List<Post> getPostsByCondition(@Param("params") Map<String, Object> params, @Param("condition") Condition condition);

	/**
	 *
	 * @param params
	 * @param condition
	 * @return
	 */
    int countPostsByCondition(@Param("params") Map<String, Object> params, @Param("condition") Condition condition);
}
