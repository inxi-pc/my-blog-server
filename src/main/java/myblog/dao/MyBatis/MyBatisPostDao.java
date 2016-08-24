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

    public Post insertPost(Post insert) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.insertPost(insert);
    }

    public boolean deletePostById(int postId) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.deletePostById(postId);
    }

    public boolean updatePost(int postId, Post update) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);

        return postMapper.updatePost(postId, update);
    }

    public Post getPostById(int postId) {
        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
        PostMapper postMapper = session.getMapper(PostMapper.class);
        Post post = postMapper.getPostById(postId);

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

        if (posts == null || posts.isEmpty()) {
            throw new NotFoundException("Posts not found");
        } else {
            return posts;
        }
    }
}
