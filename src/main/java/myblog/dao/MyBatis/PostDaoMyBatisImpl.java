package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.PostMapper;
import myblog.dao.PostDao;
import myblog.domain.Post;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.exception.LiteralMessageMeta;
import org.apache.ibatis.session.SqlSession;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

public class PostDaoMyBatisImpl implements PostDao {

    /**
     * Reference of MyBatisDaoFactory instance
     */
    private MyBatisDaoFactory myBatisDaoFactory;

    /**
     * @param factory
     */
    PostDaoMyBatisImpl(MyBatisDaoFactory factory) {
        this.myBatisDaoFactory = factory;
    }

    @Override
    public int createPost(Post insert) {
        if (insert == null) {
            throw new GenericException(GenericMessageMeta.NULL_OBJECT, Post.class, Response.Status.BAD_REQUEST);
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
        if (!Post.isValidPostId(postId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, "post_id", Response.Status.BAD_REQUEST);
        }

        Post post = new Post();
        post.setPost_enabled(false);

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.updatePost(postId, post);
    }

    @Override
    public boolean updatePost(int postId, Post update) {
        if (update == null) {
            return true;
        }

        if (!Post.isValidPostId(postId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, "post_id", Response.Status.BAD_REQUEST);
        }

        update.checkFieldUpdatable();

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.updatePost(postId, update);
    }

    @Override
    public Post getPostById(int postId) {
        if (!Post.isValidPostId(postId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, "post_id", Response.Status.BAD_REQUEST);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.getPostById(postId);
    }

    @Override
    public List<Post> getPostsByIds(int[] postIds) {
        if (postIds == null) {
            throw new GenericException(GenericMessageMeta.NULL_IDS, Post.class, Response.Status.BAD_REQUEST);
        }

        if (postIds.length <= 0) {
            throw new GenericException(GenericMessageMeta.EMPTY_IDS, Post.class, Response.Status.BAD_REQUEST);
        }

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.getPostsByIds(postIds);
    }

    @Override
    public List<Post> getPostsByCondition(Map<String, Object> params) {
        if (params == null) {
            throw new GenericException(LiteralMessageMeta.NULL_QUERY_PARAM_LIST, Response.Status.BAD_REQUEST);
        }

        if (params.size() <= 0) {
            throw new GenericException(LiteralMessageMeta.EMPTY_QUERY_PARAM_LIST, Response.Status.BAD_REQUEST);
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
