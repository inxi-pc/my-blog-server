package myblog.dao.MyBatis;

import myblog.dao.PostDao;
import myblog.model.Post;

public class MyBatisPostDao implements PostDao{

    /**
     *
     * @return
     */
    public Post insertPost(Post insert) {
        return null;
    }

    /**
     *
     * @return
     */
    public boolean deletePostById(int postId) {
        return false;
    }

    /**
     *
     * @return
     */
    public Post findPostById(int postId) {
        return null;
    }

    /**
     *
     * @return
     */
    public boolean updatePost(int postId, Post update) {
        return false;
    }
}
