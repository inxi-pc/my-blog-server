package myblog.dao.mybatis;

import myblog.dao.mybatis.mapper.PostMapper;
import myblog.dao.PostDao;
import myblog.dao.sql.ConditionBuilder;
import myblog.domain.Category;
import myblog.domain.Post;
import myblog.domain.User;
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
        session.close();

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

        boolean isSucceed = postMapper.updatePost(postId, post);
        session.close();

        return isSucceed;
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

        boolean isSucceed = postMapper.updatePost(postId, update);
        session.close();

        return isSucceed;
    }

	@Override
	public Post getPostById(int postId, boolean withCategory, boolean withUser) {
		if (!Post.isValidPostId(postId)) {
			throw new GenericException(GenericMessageMeta.INVALID_ID, "post_id", Response.Status.BAD_REQUEST);
		}

		ConditionBuilder conditionBuilder = new ConditionBuilder();
		if (withCategory) {
            conditionBuilder.addLeftJoinCondition(Category.class);
		}
		if (withUser) {
			conditionBuilder.addLeftJoinCondition(User.class);
		}

		SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
		PostMapper postMapper = session.getMapper(PostMapper.class);

		Post post = postMapper.getPostById(postId, conditionBuilder.build());
        session.close();

        return post;
	}

    @Override
    public List<Post> getPostsByIds(int[] postIds, boolean withCategory, boolean withUser) {
        if (postIds == null) {
            throw new GenericException(GenericMessageMeta.NULL_IDS, Post.class, Response.Status.BAD_REQUEST);
        }

        if (postIds.length <= 0) {
            throw new GenericException(GenericMessageMeta.EMPTY_IDS, Post.class, Response.Status.BAD_REQUEST);
        }

		ConditionBuilder conditionBuilder = new ConditionBuilder();
		if (withCategory) {
			conditionBuilder.addLeftJoinCondition(Category.class);
		}
		if (withUser) {
			conditionBuilder.addLeftJoinCondition(User.class);
		}

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        List<Post> posts = postMapper.getPostsByIds(postIds, conditionBuilder.build());
        session.close();

        return posts;
    }

    @Override
    public List<Post> getPostsByCondition(Map<String, Object> params, boolean withCategory, boolean withUser) {
        if (params == null) {
            throw new GenericException(LiteralMessageMeta.NULL_QUERY_PARAM_LIST, Response.Status.BAD_REQUEST);
        }

        if (params.size() <= 0) {
            throw new GenericException(LiteralMessageMeta.EMPTY_QUERY_PARAM_LIST, Response.Status.BAD_REQUEST);
        }

		ConditionBuilder conditionBuilder = new ConditionBuilder();
		if (withCategory) {
			conditionBuilder.addLeftJoinCondition(Category.class);
		}
		if (withUser) {
			conditionBuilder.addLeftJoinCondition(User.class);
		}

        SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
        PostMapper postMapper = session.getMapper(PostMapper.class);

        List<Post> posts = postMapper.getPostsByCondition(params, conditionBuilder.build());
        session.close();

        return posts;
    }

    @Override
    public int countPostsByCondition(Map<String, Object> params, boolean withCategory, boolean withUser) {
		if (params == null) {
			throw new GenericException(LiteralMessageMeta.NULL_QUERY_PARAM_LIST, Response.Status.BAD_REQUEST);
		}

		if (params.size() <= 0) {
			throw new GenericException(LiteralMessageMeta.EMPTY_QUERY_PARAM_LIST, Response.Status.BAD_REQUEST);
		}

		ConditionBuilder conditionBuilder = new ConditionBuilder();
		if (withCategory) {
			conditionBuilder.addLeftJoinCondition(Category.class);
		}
		if (withUser) {
			conditionBuilder.addLeftJoinCondition(User.class);
		}

		SqlSession session = this.myBatisDaoFactory.getDefaultSqlSessionFactory().openSession(true);
		PostMapper postMapper = session.getMapper(PostMapper.class);

		int count = postMapper.countPostsByCondition(params, conditionBuilder.build());
		session.close();

		return count;
    }
}
