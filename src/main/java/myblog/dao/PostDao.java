package myblog.dao;

import myblog.domain.Post;
import myblog.exception.DaoException;
import myblog.exception.DomainException;

import java.util.List;
import java.util.Map;

public interface PostDao {

    /**
     *
     * @param insert
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    int createPost(Post insert) throws DomainException, DaoException;

    /**
     *
     * @param postId
     * @return
     * @throws DaoException
     */
    boolean deletePost(int postId) throws DaoException;

    /**
     *
     * @param postId
     * @param update
     * @return
     * @throws DomainException
     * @throws DaoException
     */
    boolean updatePost(int postId, Post update) throws DomainException, DaoException;

    /**
     *
     * @param postId
     * @return
     * @throws DaoException
     */
    Post getPostById(int postId) throws DaoException;

    /**
     *
     * @param postIds
     * @return
     * @throws DaoException
     */
    List<Post> getPostsByIds(int[] postIds) throws DaoException;

    /**
     *
     * @param params
     * @return
     * @throws DaoException
     */
    List<Post> getPostsByCondition(Map<String, Object> params) throws DaoException;

    /**
     *
     * @return
     */
    int countAllPost();
}
