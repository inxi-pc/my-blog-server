package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.PostDaoMyBatisImpl;
import myblog.domain.Category;
import myblog.domain.Pagination;
import myblog.domain.Post;
import myblog.domain.Sort;
import myblog.exception.DaoException;
import myblog.exception.DomainException;
import myblog.exception.HttpExceptionFactory;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
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

        try {
            return myBatisPostDao.createPost(insert);
        } catch (DomainException | DaoException e) {
            throw HttpExceptionFactory.produce(InternalServerErrorException.class, e);
        }
    }

    /**
     *
     * @param postId
     * @return
     */
    public static boolean deletePost(int postId) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        try {
            if (myBatisPostDao.getPostById(postId) != null) {
                return myBatisPostDao.deletePost(postId);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.NOT_FOUND,
                        Category.class,
                        HttpExceptionFactory.Reason.NOT_EXIST);
            }
        } catch (DaoException e) {
            throw HttpExceptionFactory.produce(InternalServerErrorException.class, e);
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

        try {
            if (myBatisPostDao.getPostById(postId) != null) {
                update.setDefaultPost_updated_at();

                return myBatisPostDao.updatePost(postId, update);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.NOT_FOUND,
                        Category.class,
                        HttpExceptionFactory.Reason.NOT_EXIST);
            }
        } catch (DomainException | DaoException e) {
            throw HttpExceptionFactory.produce(InternalServerErrorException.class, e);
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

        try {
            return myBatisPostDao.getPostById(postId);
        } catch (DaoException e) {
            throw HttpExceptionFactory.produce(InternalServerErrorException.class, e);
        }
    }

    /**
     *
     * @param post
     * @return
     */
    public static List<Post> getPosts(Post post) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        try {
            HashMap<String, Object> params = post.convertToHashMap(null);

            return myBatisPostDao.getPostsByCondition(params);
        } catch (DomainException | DaoException e) {
            throw HttpExceptionFactory.produce(InternalServerErrorException.class, e);
        }
    }

    /**
     *
     * @param post
     * @param page
     * @param sort
     * @return
     */
    public static Pagination<Post> getPostList(Post post, Pagination<Post> page, Sort sort) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        List<Post> posts = null;
        try {
            HashMap<String, Object> params = post.convertToHashMap(null);
            params.put("limit", page.getLimit());
            params.put("offset", page.getOffset());
            params.put("orderBy", sort.getOrder_by());
            params.put("orderType", sort.getOrder_type());

            posts = myBatisPostDao.getPostsByCondition(params);
        } catch (DomainException | DaoException e) {
            throw HttpExceptionFactory.produce(InternalServerErrorException.class, e);
        }

        page.setData(posts);
        page.setRecordsTotal(myBatisPostDao.countAllPost());

        return page;
    }
}
