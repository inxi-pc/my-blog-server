package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.PostMapper;
import myblog.dao.PostDao;
import myblog.model.persistent.Post;
import org.apache.ibatis.session.SqlSession;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Map;

public class MyBatisPostDao implements PostDao {

    /**
     * Reference of MyBatisDaoFactory instance
     */
    private MyBatisDaoFactory myBatisDaoFactory;

    /**
     * @param factory
     */
    MyBatisPostDao(MyBatisDaoFactory factory) {
        this.myBatisDaoFactory = factory;
    }

    public int createPost(Post insert) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);
        postMapper.createPost(insert);
        session.commit();
        session.close();

        return insert.post_id;
    }

    public boolean deletePostById(int postId) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);
        boolean isSucceed = postMapper.deletePostById(postId);
        session.commit();
        session.close();

        return isSucceed;
    }

    public boolean updatePost(int postId, Post update) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);
        boolean isSucceed = postMapper.updatePost(update);
        session.commit();
        session.close();

        return isSucceed;
    }

    public Post getPostById(int postId) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);
        Post post = postMapper.getPostById(postId);
        session.commit();
        session.close();

        if (post == null) {
            throw new NotFoundException("Post not found");
        } else {
            return post;
        }
    }

    public List<Post> getPostsByIds(int[] postIds) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);
        List<Post> posts = postMapper.getPostsByIds(postIds);
        session.commit();
        session.close();

        if (posts == null || posts.isEmpty()) {
            throw new NotFoundException("Posts not found");
        } else {
            return posts;
        }
    }

    public List<Post> getPostsByCondition(Map<String, Object> params) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);
        List<Post> posts = postMapper.getPostsByCondition(params);
        session.commit();
        session.close();

        if (posts == null || posts.isEmpty()) {
            throw new NotFoundException("Posts not found");
        } else {
            return posts;
        }
    }
}
