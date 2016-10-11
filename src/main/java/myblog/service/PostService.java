package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.PostDaoMyBatisImpl;
import myblog.domain.Order;
import myblog.domain.Pagination;
import myblog.domain.Post;

import java.util.HashMap;
import java.util.List;

public class PostService {

    /**
     *
     * @param insert
     * @return
     */
    public static int createPost(Post insert) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.createPost(insert);
    }

    /**
     *
     * @param update
     * @return
     */
    public static boolean updatePost(Post update) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.updatePost(update);
    }

    /**
     *
     * @param postId
     * @return
     */
    public static boolean deletePost(int postId) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.deletePost(postId);
    }

    /**
     *
     * @param postId
     * @return
     */
    public static Post getPostById(int postId) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.getPostById(postId);
    }

    /**
     *
     * @param post
     * @return
     */
    public static List<Post> getPosts(Post post) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();
        HashMap<String, Object> params = post.convertToHashMap();

        return myBatisPostDao.getPostsByCondition(params);
    }

    /**
     *
     * @param post
     * @param page
     * @param order
     * @return
     */
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
