package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.PostMapper;
import myblog.dao.PostDao;
import myblog.domain.Post;
import myblog.exception.FieldNotInsertableException;
import myblog.exception.FieldNotNullableException;
import myblog.exception.FieldNotUpdatableException;
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
    @Override
    public int createPost(Post insert) {
        if (insert == null) {
            throw new NullPointerException("Unexpected category: " + "Null pointer");
        }

        insert.setDefaultableFieldValue();

        try {
            insert.checkFieldInsertable();
        } catch (FieldNotInsertableException | FieldNotNullableException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
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
    @Override
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
    @Override
    public boolean updatePost(int postId, Post update) {
        if (update == null) {
            throw new NullPointerException("Unexpected post: " + "Null pointer");
        }

        try {
            update.checkFieldUpdatable();
        } catch (FieldNotUpdatableException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
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
    @Override
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
    @Override
    public List<Post> getPostsByIds(int[] postIds) {
        if (postIds == null) {
            throw new IllegalArgumentException("Unexpected post ids: " + "Null pointer");
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
    @Override
    public List<Post> getPostsByCondition(Map<String, Object> params) {
        if (params == null) {
            throw new IllegalArgumentException("Unexpected params: " + "Null pointer");
        }

        if (params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.getPostsByCondition(params);
        } else {
            throw new IllegalArgumentException("Unexpected params: Empty value");
        }
    }

    /**
     *
     * @return
     */
    @Override
    public int countAllPost() {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.countAllPost();
    }
}
