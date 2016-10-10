package myblog.dao.MyBatis.Mapper;

import myblog.domain.Post;

import java.util.List;
import java.util.Map;

public interface PostMapper {
    /**
     *
     * @return
     */
    int createPost(Post insert);

    /**
     *
     * @return
     */
    boolean deletePostById(int postId);

    /**
     *
     * @return
     */
    boolean updatePost(Post update);

    /**
     *
     * @return
     */
    Post getPostById(int postId);

    /**
     *
     * @return
     */
    List<Post> getPostsByIds(int[] postIds);

    /**
     *
     * @param params
     * @return
     */
    List<Post> getPostsByCondition(Map<String, Object> params);
}
