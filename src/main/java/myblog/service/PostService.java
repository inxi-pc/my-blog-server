package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.MyBatisPostDao;
import myblog.model.business.OrderBo;
import myblog.model.business.PaginationBo;
import myblog.model.persistent.Post;

import java.util.HashMap;
import java.util.List;

public class PostService {

    public static int createPost(Post insert) {
        MyBatisPostDao myBatisPostDao = (MyBatisPostDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.createPost(insert);
    }

    public static boolean updatePost(int postId, Post update) {
        MyBatisPostDao myBatisPostDao = (MyBatisPostDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.updatePost(postId, update);
    }

    public static Post getPostById(int postId) {
        MyBatisPostDao myBatisPostDao = (MyBatisPostDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.getPostById(postId);
    }

    public static List<Post> getPostList(PaginationBo page, OrderBo order) {
        MyBatisPostDao myBatisPostDao = (MyBatisPostDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("limit", page.getLimit());
        params.put("offset", page.getOffset());
        params.put("orderBy", order.getOrder_by());
        params.put("orderType", order.getOrder_type());

        return myBatisPostDao.getPostsByCondition(params);
    }
}
