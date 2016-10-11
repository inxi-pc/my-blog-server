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
        if (!insert.checkAllFieldsIsNull()) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            postMapper.createPost(insert);
            session.commit();
            session.close();

            return insert.getPost_id();
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
        if (!update.checkAllFieldsIsNullExceptPK()) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            boolean isSucceed = postMapper.updatePost(update);
            session.commit();
            session.close();

            return isSucceed;
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
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            boolean isSucceed = postMapper.updatePost(post);
            session.commit();
            session.close();

            return isSucceed;
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
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            Post post = postMapper.getPostById(postId);
            session.commit();
            session.close();

            return post;
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
        if (postIds.length > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            List<Post> posts = postMapper.getPostsByIds(postIds);
            session.commit();
            session.close();

            return posts;
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
        if (params.size() > 0) {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            List<Post> posts = postMapper.getPostsByCondition(params);
            session.commit();
            session.close();

            return posts;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
