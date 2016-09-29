package myblog.dao;

import myblog.model.persistent.Post;

import java.util.List;
import java.util.Map;

public interface PostDao {
    /**
     *
     * @param insert
     * @return
     */
    int createPost(Post insert);

    /**
     *
     * @param postId
     * @return
     */
    boolean deletePostById(int postId);

    /**
     *
     * @param postId
     * @param update
     * @return
     */
    boolean updatePost(int postId, Post update);

    /**
     *
     * @param postId
     * @return
     */
    Post getPostById(int postId);

    /**
     *
     * @param postIds
     * @return
     */
    List<Post> getPostListByIds(int[] postIds);

    /**
     *
     * @param params
     * @return
     */
    List<Post> getPostListByCondition(Map<String, Object> params);
}
