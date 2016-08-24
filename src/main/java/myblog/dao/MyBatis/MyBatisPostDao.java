package myblog.dao.MyBatis;

import myblog.dao.MyBatis.Mapper.PostMapper;
import myblog.dao.PostDao;
import myblog.exception.InternalException;
import myblog.exception.NotFoundException;
import myblog.model.Post;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class MyBatisPostDao implements PostDao {
    /**
     *
     */
    private MyBatisDaoFactory myBatisDaoFactory;

    /**
     * @param factory
     */
    MyBatisPostDao(MyBatisDaoFactory factory) {
        this.myBatisDaoFactory = factory;
    }


    public Post insertPost(Post insert) {
        try {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.insertPost(insert);
        } catch (Exception e) {
            throw new InternalException(e);
        }
    }

    public boolean deletePostById(int postId) {
        try {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.deletePostById(postId);
        } catch (Exception e) {
            throw new InternalException(e);
        }
    }

    public boolean updatePost(int postId, Post update) {
        try {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);

            return postMapper.updatePost(postId, update);
        } catch (Exception e) {
            throw new InternalException(e);
        }
    }

    public Post getPostById(int postId) {
        try {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            Post post = postMapper.getPostById(postId);

            if (post == null) {
                throw new NotFoundException("Post not found");
            } else {
                return post;
            }
        } catch (Exception e) {
            throw new InternalException(e);
        }
    }

    public List<Post> getPostListByIds(int[] postIds) {
        try {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            List<Post> posts = postMapper.getPostListByIds(postIds);

            if (posts.isEmpty()) {
                throw new NotFoundException("Posts not found");
            } else {
                return posts;
            }
        } catch (Exception e) {
            throw new InternalException(e);
        }
    }

    public List<Post> getPostListByCondition(Map<String, Object> params) {
        try {
            SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession();
            PostMapper postMapper = session.getMapper(PostMapper.class);
            List<Post> posts = postMapper.getPostListByCondition(params);

            if (posts.isEmpty()) {
                throw new NotFoundException("Posts not found");
            } else {
                return posts;
            }
        } catch (Exception e) {
            throw new InternalException(e);
        }
    }
}
