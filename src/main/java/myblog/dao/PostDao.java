package myblog.dao;

import myblog.model.Post;

public interface PostDao {
    /**
     *
     * @return
     */
    public Post insertPost(Post insert);

    /**
     *
     * @return
     */
    public boolean deletePostById(int postId);

    /**
     *
     * @return
     */
    public Post findPostById(int postId);

    /**
     *
     * @return
     */
    public boolean updatePost(int postId, Post update);
}
