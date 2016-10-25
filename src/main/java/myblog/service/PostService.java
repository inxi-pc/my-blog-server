package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.PostDaoMyBatisImpl;
import myblog.domain.Pagination;
import myblog.domain.Post;
import myblog.domain.Sort;

import javax.ws.rs.NotFoundException;
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

        insert.setDefaultPost_enabled();
        insert.setDefaultPost_created_at();
        insert.setDefaultPost_updated_at();

        return myBatisPostDao.createPost(insert);
    }

    /**
     *
     * @param postId
     * @return
     */
    public static boolean deletePost(int postId) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        if (PostService.getPostById(postId) != null) {
            return myBatisPostDao.deletePost(postId);
        } else {
            throw new NotFoundException("Not found post: Id = " + postId);
        }
    }

    /**
     *
     * @param postId
     * @param update
     * @return
     */
    public static boolean updatePost(int postId, Post update) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();
        if (PostService.getPostById(postId) != null) {
            update.setDefaultPost_updated_at();

            return myBatisPostDao.updatePost(postId, update);
        } else {
            throw new NotFoundException("Not found post: Id = " + postId);
        }
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
    public static Pagination<Post> getPostList(Post post, Pagination<Post> page, Sort order) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        HashMap<String, Object> params = post.convertToHashMap();
        params.put("limit", page.getLimit());
        params.put("offset", page.getOffset());
        params.put("orderBy", order.getOrder_by());
        params.put("orderType", order.getOrder_type());

        List<Post> posts = myBatisPostDao.getPostsByCondition(params);
        page.setData(posts);
        page.setTotal(posts.size());

        return page;
    }
}
