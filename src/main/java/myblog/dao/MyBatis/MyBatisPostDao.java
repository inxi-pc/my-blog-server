package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.PostMapper;
import myblog.dao.PostDao;
import myblog.exception.NotFoundException;
import myblog.model.Post;
import org.apache.ibatis.session.SqlSession;

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
        session.commit();
        session.close();

        return postMapper.deletePostById(postId);
    }

    public boolean updatePost(int postId, Post update) {
        update.post_id = postId;

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.updatePost(update);
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

    public List<Post> getPostListByIds(int[] postIds) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);
        List<Post> posts = postMapper.getPostListByIds(postIds);
        session.commit();
        session.close();

        if (posts == null || posts.isEmpty()) {
            throw new NotFoundException("Posts not found");
        } else {
            return posts;
        }
    }

    public List<Post> getPostListByCondition(Map<String, Object> params) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);
        List<Post> posts = postMapper.getPostListByCondition(params);
        session.commit();
        session.close();

        if (posts == null || posts.isEmpty()) {
            throw new NotFoundException("Posts not found");
        } else {
            return posts;
        }
    }
}
