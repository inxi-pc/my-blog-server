package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.MyBatisCategoryDao;
import myblog.model.persistence.Category;
import myblog.model.persistence.Order;
import myblog.model.persistence.Pagination;

import java.util.HashMap;
import java.util.List;

public class CategoryService {

    public static Pagination<Category> getCategoryList(Pagination<Category> page, Order order) {
        MyBatisCategoryDao myBatisCategoryDao = (MyBatisCategoryDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("limit", page.getLimit());
        params.put("offset", page.getOffset());
        params.put("order_by", order.getOrder_by());
        params.put("order_type", order.getOrder_type());

        List<Category> categories = myBatisCategoryDao.getCategoriesByCondition(params);
        page.setData(categories);

        return page;
    }
}
