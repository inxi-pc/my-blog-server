package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.PostMapper;
import myblog.dao.PostDao;
import myblog.domain.Post;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Field;
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
        if (insert == null) {
            throw new NullPointerException("Unexpected category: " + "Null pointer");
        }

        Field field;
        if ((field = insert.checkInsertConstraint()) != null) {
            throw new IllegalArgumentException("Unexpected " + field.getName() + ": " + "Invalid value");
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);
        postMapper.createPost(insert);

        return insert.getPost_id();
    }

    /**
     *
     * @param postId
     * @return
     */
    public boolean deletePost(int postId) {
        if (Post.isValidPostId(postId)) {
            Post post = new Post();
            post.setPost_enabled(false);

            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.updatePost(postId, post);
        } else {
            throw new IllegalArgumentException("Unexpected post id: Invalid value");
        }
    }

    /**
     *
     * @param postId
     * @param update
     * @return
     */
    public boolean updatePost(int postId, Post update) {
        if (update == null) {
            throw new NullPointerException("Unexpected category: " + "Null pointer");
        }

        Field field;
        if ((field = update.checkUpdateConstraint()) != null) {
            throw new IllegalArgumentException("Unexpected " + field.getName() + ": " + "Invalid value");
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.updatePost(postId, update);
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
            throw new IllegalArgumentException("Unexpected post id: Invalid value");
        }
    }

    /**
     *
     * @param postIds
     * @return
     */
    public List<Post> getPostsByIds(int[] postIds) {
        if (postIds == null) {
            throw new NullPointerException("Unexpected post ids: " + "Null pointer");
        }

        if (postIds.length > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.getPostsByIds(postIds);
        } else {
            throw new IllegalArgumentException("Unexpected post ids: Empty value");
        }
    }

    /**
     *
     * @param params
     * @return
     */
    public List<Post> getPostsByCondition(Map<String, Object> params) {
        if (params == null) {
            throw new NullPointerException("Unexpected params: " + "Null pointer");
        }

        if (params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.getPostsByCondition(params);
        } else {
            throw new IllegalArgumentException("Unexpected params: Empty value");
        }
    }
}
