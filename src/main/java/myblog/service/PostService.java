package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.PostDaoMyBatisImpl;
import myblog.domain.Order;
import myblog.domain.Pagination;
import myblog.domain.Post;

import java.util.HashMap;
import java.util.List;

public class PostService {

    public static int createPost(Post insert) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.createPost(insert);
    }

    public static boolean updatePost(Post update) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.updatePost(update);
    }

    public static Post getPostById(int postId) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.getPostById(postId);
    }

    public static List<Post> getPosts(Post post) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();
        HashMap<String, Object> params = post.convertToHashMap();

        return myBatisPostDao.getPostsByCondition(params);
    }

    public static Pagination<Post> getPostList(Post post, Pagination<Post> page, Order order) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();
        HashMap<String, Object> params = post.convertToHashMap();

        params.put("limit", page.getLimit());
        params.put("offset", page.getOffset());
        params.put("orderBy", order.getOrder_by());
        params.put("orderType", order.getOrder_type());

        List<Post> posts = myBatisPostDao.getPostsByCondition(params);
        page.setData(posts);

        return page;
    }
}
