package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.PostDaoMyBatisImpl;
import myblog.dao.Sql.Pagination;
import myblog.dao.Sql.Sort;
import myblog.domain.Post;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;

import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

public class PostService {

    /**
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
     * @param postId
     * @return
     */
    public static boolean deletePost(int postId) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        if (myBatisPostDao.getPostById(postId) != null) {
            return myBatisPostDao.deletePost(postId);
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, Post.class, Response.Status.BAD_REQUEST);
        }
    }

    /**
     * @param postId
     * @param update
     * @return
     */
    public static boolean updatePost(int postId, Post update) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        if (myBatisPostDao.getPostById(postId) != null) {
            update.setDefaultPost_updated_at();

            return myBatisPostDao.updatePost(postId, update);
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, Post.class, Response.Status.BAD_REQUEST);
        }
    }

    /**
     *
     * @param postId
     * @param withCategory
     * @param withUser
     * @return
     */
    public static Post getPostById(int postId, boolean withCategory, boolean withUser) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.getPostById(postId, withCategory, withUser);

    }

    /**
     *
     * @param post
     * @param withCategory
     * @param withUser
     * @return
     */
    public static List<Post> getPosts(Post post, boolean withCategory, boolean withUser) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        HashMap<String, Object> params = post.convertToHashMap(null);

        return myBatisPostDao.getPostsByCondition(params, withCategory, withUser);
    }

    /**
     *
     * @param post
     * @param page
     * @param sort
     * @param withCategory
     * @param withUser
     * @return
     */
    public static Pagination<Post> getPostList(Post post,
                                               Pagination<Post> page,
                                               Sort sort,
                                               boolean withCategory,
                                               boolean withUser) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        HashMap<String, Object> params = post.convertToHashMap(null);
        params.put("limit", page.getLimit());
        params.put("offset", page.getOffset());
        params.put("orderBy", sort.getOrder_by());
        params.put("orderType", sort.getOrder_type());
        List<Post> posts = myBatisPostDao.getPostsByCondition(params, withCategory, withUser);

        page.setData(posts);
        page.setRecordsTotal(myBatisPostDao.countAllPost());

        return page;
    }
}
