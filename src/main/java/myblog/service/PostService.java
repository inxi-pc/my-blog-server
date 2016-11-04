package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.PostDaoMyBatisImpl;
import myblog.domain.Pagination;
import myblog.domain.Post;
import myblog.domain.Sort;
import myblog.exception.DaoException;
import myblog.exception.DomainException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.util.HashMap;
import java.util.List;

public class PostService {

    public static int createPost(Post insert) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        insert.setDefaultPost_enabled();
        insert.setDefaultPost_created_at();
        insert.setDefaultPost_updated_at();

        try {
            return myBatisPostDao.createPost(insert);
        } catch (DomainException | DaoException e) {
            throw new BadRequestException(e.getMessage(), e);
        }
    }

    public static boolean deletePost(int postId) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        try {
            if (myBatisPostDao.getPostById(postId) != null) {
                return myBatisPostDao.deletePost(postId);
            } else {
                throw new NotFoundException("Not found post: Post id = " + postId);
            }
        } catch (DaoException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }

    public static boolean updatePost(int postId, Post update) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        try {
            if (myBatisPostDao.getPostById(postId) != null) {
                update.setDefaultPost_updated_at();

                return myBatisPostDao.updatePost(postId, update);
            } else {
                throw new NotFoundException("Not found post: Post id = " + postId);
            }
        } catch (DomainException | DaoException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }

    public static Post getPostById(int postId) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        try {
            return myBatisPostDao.getPostById(postId);
        } catch (DaoException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }

    public static List<Post> getPosts(Post post) {
        PostDaoMyBatisImpl myBatisPostDao = (PostDaoMyBatisImpl)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        List<Post> posts = null;
        try {
            HashMap<String, Object> params = post.convertToHashMap(null);
            posts = myBatisPostDao.getPostsByCondition(params);
        } catch (DomainException | DaoException e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }

        if (posts != null && posts.size() <= 0) {
            return posts;
        } else {
            throw new NotFoundException("Not found posts");
        }
    }

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
            throw new InternalServerErrorException(e.getMessage(), e);
        }

        if (posts != null && posts.size() > 0) {
            page.setData(posts);
            page.setRecordsTotal(myBatisPostDao.countAllPost());

            return page;
        } else {
            throw new NotFoundException("Not found post list");
        }
    }
}
