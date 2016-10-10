package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.PostMapper;
import myblog.dao.PostDao;
import myblog.domain.Post;
import org.apache.ibatis.session.SqlSession;

import javax.ws.rs.InternalServerErrorException;
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
        if (!insert.checkAllFieldsIsNull()) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            postMapper.createPost(insert);
            session.commit();
            session.close();

            return insert.getPost_id();
        } else {
            throw new InternalServerErrorException();
        }
    }

    /**
     *
     * @param postId
     * @return
     */
    public boolean deletePostById(int postId) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);
        boolean isSucceed = postMapper.deletePostById(postId);
        session.commit();
        session.close();

        return isSucceed;
    }

    /**
     *
     * @param update
     * @return
     */
    public boolean updatePost(Post update) {
        if (!update.checkAllFieldsIsNullExceptPK()) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            boolean isSucceed = postMapper.updatePost(update);
            session.commit();
            session.close();

            return isSucceed;
        } else {
            throw new InternalServerErrorException();
        }
    }

    /**
     *
     * @param postId
     * @return
     */
    public Post getPostById(int postId) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);
        Post post = postMapper.getPostById(postId);
        session.commit();
        session.close();

        return post;
    }

    /**
     *
     * @param postIds
     * @return
     */
    public List<Post> getPostsByIds(int[] postIds) {
        if (postIds.length > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            List<Post> posts = postMapper.getPostsByIds(postIds);
            session.commit();
            session.close();

            return posts;
        } else {
            throw new InternalServerErrorException();
        }
    }

    /**
     *
     * @param params
     * @return
     */
    public List<Post> getPostsByCondition(Map<String, Object> params) {
        if (params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            List<Post> posts = postMapper.getPostsByCondition(params);
            session.commit();
            session.close();

            return posts;
        } else {
            throw new InternalServerErrorException();
        }
    }
}
