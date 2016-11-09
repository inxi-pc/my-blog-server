package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.PostMapper;
import myblog.dao.PostDao;
import myblog.domain.Post;
import myblog.exception.DaoException;
import myblog.exception.DomainException;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Array;
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
     * @throws DomainException
     * @throws DaoException
     */
    @Override
    public int createPost(Post insert) throws DomainException, DaoException {
        if (insert == null) {
            throw new DaoException(Post.class, DaoException.Type.NULL_POINTER);
        }

        try {
            insert.setDefaultableFieldValue();
            insert.checkFieldInsertable();
        } catch (DomainException e) {
            throw e;
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
     * @throws DaoException
     */
    @Override
    public boolean deletePost(int postId) throws DaoException {
        if (Post.isValidPostId(postId)) {
            Post post = new Post();
            post.setPost_enabled(false);

            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.updatePost(postId, post);
        } else {
            throw new DaoException(Integer.class, DaoException.Type.INVALID_PARAM);
        }
    }

    /**
     *
     * @param postId
     * @param update
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    @Override
    public boolean updatePost(int postId, Post update) throws DomainException, DaoException {
        if (update == null) {
            return true;
        }

        if (!Post.isValidPostId(postId)) {
            throw new DaoException(Integer.class, DaoException.Type.INVALID_PARAM);
        }

        try {
            update.checkFieldUpdatable();
        } catch (DomainException e) {
            throw e;
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.updatePost(postId, update);
    }

    /**
     *
     * @param postId
     * @return
     * @throws DaoException
     */
    @Override
    public Post getPostById(int postId) throws DaoException {
        if (Post.isValidPostId(postId)) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.getPostById(postId);
        } else {
            throw new DaoException(Integer.class, DaoException.Type.INVALID_PARAM);
        }
    }

    /**
     *
     * @param postIds
     * @return
     * @throws DaoException
     */
    @Override
    public List<Post> getPostsByIds(int[] postIds) throws DaoException {
        if (postIds == null) {
            throw new DaoException(Array.class, DaoException.Type.NULL_POINTER);
        }

        if (postIds.length > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.getPostsByIds(postIds);
        } else {
            throw new DaoException(Array.class, DaoException.Type.EMPTY_VALUE);
        }
    }

    /**
     *
     * @param params
     * @return
     * @throws DaoException
     */
    @Override
    public List<Post> getPostsByCondition(Map<String, Object> params) throws DaoException {
        if (params == null) {
            throw new DaoException(Map.class, DaoException.Type.NULL_POINTER);
        }

        if (params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.getPostsByCondition(params);
        } else {
            throw new DaoException(Map.class, DaoException.Type.EMPTY_VALUE);
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
