package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.PostMapper;
import myblog.dao.PostDao;
import myblog.domain.Post;
import myblog.exception.DaoException;
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

    @Override
    public int createPost(Post insert) {
        if (insert == null) {
            throw new DaoException(DaoException.Type.INSERT_NULL_OBJECT);
        }

        insert.setDefaultableFieldValue();
        insert.checkFieldInsertable();

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);
        postMapper.createPost(insert);

        return insert.getPost_id();
    }

    @Override
    public boolean deletePost(int postId) {
        if (Post.isValidPostId(postId)) {
            throw new DaoException(DaoException.Type.INVALID_DELETED_ID);
        }

        Post post = new Post();
        post.setPost_enabled(false);

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.updatePost(postId, post);
    }

    @Override
    public boolean updatePost(int postId, Post update)  {
        if (update == null) {
            return true;
        }

        if (!Post.isValidPostId(postId)) {
            throw new DaoException(DaoException.Type.INVALID_UPDATED_ID);
        }

        update.checkFieldUpdatable();

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.updatePost(postId, update);
    }

    @Override
    public Post getPostById(int postId) {
        if (!Post.isValidPostId(postId)) {
            throw new DaoException(DaoException.Type.INVALID_QUERY_ID);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.getPostById(postId);
    }

    @Override
    public List<Post> getPostsByIds(int[] postIds) throws DaoException {
        if (postIds == null) {
            throw new DaoException(DaoException.Type.NULL_QUERY_PARAM);
        }

        if (postIds.length <= 0) {
            throw new DaoException(DaoException.Type.EMPTY_QUERY_PARAM);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.getPostsByIds(postIds);
    }

    @Override
    public List<Post> getPostsByCondition(Map<String, Object> params) throws DaoException {
        if (params == null) {
            throw new DaoException(DaoException.Type.NULL_QUERY_PARAM);
        }

        if (params.size() <= 0) {
            throw new DaoException(DaoException.Type.EMPTY_QUERY_PARAM);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.getPostsByCondition(params);
    }

    @Override
    public int countAllPost() {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.countAllPost();
    }
}
