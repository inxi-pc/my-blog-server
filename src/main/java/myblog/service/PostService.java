package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.MyBatisPostDao;
import myblog.model.SqlOrder;
import myblog.model.SqlPagination;
import myblog.model.Post;

import java.util.HashMap;
import java.util.List;

public class PostService {

    public static int createPost(Post insert) {
        MyBatisPostDao myBatisPostDao = (MyBatisPostDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.createPost(insert);
    }

    public static boolean updatePost(int post_id, Post update) {
        MyBatisPostDao myBatisPostDao = (MyBatisPostDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.updatePost(post_id, update);
    }

    public static Post getPostById(int postId) {
        MyBatisPostDao myBatisPostDao = (MyBatisPostDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.getPostById(postId);
    }

    public static List<Post> getPosts(SqlPagination page, SqlOrder order) {
        MyBatisPostDao myBatisPostDao = (MyBatisPostDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("limit", page.getLimit());
        params.put("offset", page.getOffset());
        params.put("orderBy", order.getOrderBy());
        params.put("orderType", order.getOrderType());

        return myBatisPostDao.getPostListByCondition(params);
    }
}
