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
	 *
	 * @param postId
	 * @param withCategory
	 * @param withUser
	 * @return
	 */
	Post getPostById(int postId, boolean withCategory, boolean withUser);

	/**
	 * Wrapped method for getPostById
	 *
	 * @param postId
	 * @return
	 */
	default Post getPostById(int postId) {
		return getPostById(postId, false, false);
	}

    /**
     *
     * @param postIds
     * @param withCategory
     * @param withUser
     * @return
     */
    List<Post> getPostsByIds(int[] postIds, boolean withCategory, boolean withUser);


	/**
	 * Wrapped method for getPostsByIds
	 *
	 * @param postIds
	 * @return
	 */
	default List<Post> getPostsByIds(int[] postIds) {
		return getPostsByIds(postIds, false, false);
	}

    /**
     *
     * @param params
     * @param withCategory
     * @param withUser
     * @return
     */
    List<Post> getPostsByCondition(Map<String, Object> params, boolean withCategory, boolean withUser);


	/**
	 * Wrapped method for getPostsByCondition
	 *
	 * @param params
	 * @return
	 */
	default List<Post> getPostsByCondition(Map<String, Object> params) {
		return getPostsByCondition(params, false, false);
	}

	/**
	 *
	 * @param params
	 * @param withCategory
	 * @param withUser
	 * @return
	 */
    int countPostsByCondition(Map<String, Object> params, boolean withCategory, boolean withUser);
}
