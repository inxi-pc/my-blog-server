package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.PostMapper;
import myblog.dao.PostDao;
import myblog.domain.Post;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class PostDaoMyBatisImpl implements PostDao {

    /**
     * Reference of MyBatisDaoFactory instance
     *
     */
    private MyBatisDaoFactory myBatisDaoFactory;

    /**
     * @param factory
     *
     */
    PostDaoMyBatisImpl(MyBatisDaoFactory factory) {
        this.myBatisDaoFactory = factory;
    }

    /**
     *
     * @param insert
     * @return
     */
    public int createPost(Post insert) {
        if (insert != null && !insert.checkAllFieldsIsNullExceptPK()) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);
            postMapper.createPost(insert);

            return insert.getPost_id();
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param postId
     * @return
     */
    public boolean deletePost(int postId) {
        if (Post.isValidPostId(postId)) {
            Post post = new Post();
            post.setPost_id(postId);
            post.setPost_enabled(false);

            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.updatePost(post);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param update
     * @return
     */
    public boolean updatePost(Post update) {
        if (update != null && !update.checkAllFieldsIsNullExceptPK()) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.updatePost(update);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param postId
     * @return
     */
    public Post getPostById(int postId) {
        if (Post.isValidPostId(postId)) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.getPostById(postId);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param postIds
     * @return
     */
    public List<Post> getPostsByIds(int[] postIds) {
        if (postIds != null && postIds.length > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.getPostsByIds(postIds);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param params
     * @return
     */
    public List<Post> getPostsByCondition(Map<String, Object> params) {
        if (params != null && params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.getPostsByCondition(params);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
