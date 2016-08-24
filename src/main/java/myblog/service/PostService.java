package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.MyBatisPostDao;
import myblog.model.Post;

public class PostService {

    public static Post getPostById(int postId) {
        MyBatisPostDao myBatisPostDao = (MyBatisPostDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getPostDao();

        return myBatisPostDao.getPostById(postId);
    }
}
