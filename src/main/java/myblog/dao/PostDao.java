package myblog.dao;

import myblog.domain.Post;

import java.util.List;
import java.util.Map;

public interface PostDao {

    /**
     * @param insert
     * @return
     */
    int createPost(Post insert);

    /**
     * @param postId
     * @return
     */
    boolean deletePost(int postId);

    /**
     * @param postId
     * @param update
     * @return
     */
    boolean updatePost(int postId, Post update);

    /**
     * @param postId
     * @return
     */
    Post getPostById(int postId);

	/**
	 *
	 * @param postId
	 * @param withCategory
	 * @param withUser
	 * @return
	 */
	Post getPostById(int postId, boolean withCategory, boolean withUser);

    /**
     *
     * @param postIds
     * @param withCategory
     * @param withUser
     * @return
     */
    List<Post> getPostsByIds(int[] postIds, boolean withCategory, boolean withUser);


    /**
     *
     * @param params
     * @param withCategory
     * @param withUser
     * @return
     */
    List<Post> getPostsByCondition(Map<String, Object> params, boolean withCategory, boolean withUser);

    /**
     * @return
     */
    int countAllPost();
}
