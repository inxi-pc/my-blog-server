package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.MyBatisPostDao;
import myblog.model.Post;

public class PostService {

    public static int createPost(Post insert) {
        MyBatisPostDao myBatisPostDao = (MyBatisPostDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();
        int post_id = myBatisPostDao.createPost(insert);

        return post_id;
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
}
