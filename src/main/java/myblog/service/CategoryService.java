package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.MyBatisCategoryDao;
import myblog.model.Category;
import myblog.model.SqlOrder;
import myblog.model.SqlPagination;

import java.util.HashMap;
import java.util.List;

public class CategoryService {

    public static List<Category> getCategories(SqlPagination page, SqlOrder order) {
        MyBatisCategoryDao myBatisCategoryDao = (MyBatisCategoryDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("limit", page.getLimit());
        params.put("offset", page.getOffset());
        params.put("orderBy", order.getOrderBy());
        params.put("orderType", order.getOrderType());

        return myBatisCategoryDao.getCategoryListByCondition(params);
    }
}
