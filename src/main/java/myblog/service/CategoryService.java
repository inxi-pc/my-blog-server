package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.MyBatisCategoryDao;
import myblog.model.persistent.Category;
import myblog.model.business.OrderBo;
import myblog.model.business.PaginationBo;

import java.util.HashMap;
import java.util.List;

public class CategoryService {

    public static List<Category> getCategoryList(PaginationBo page, OrderBo order) {
        MyBatisCategoryDao myBatisCategoryDao = (MyBatisCategoryDao)
                DaoFactory.getDaoFactory(DaoFactory.DaoBackend.MYBATIS).getCategoryDao();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("limit", page.getLimit());
        params.put("offset", page.getOffset());
        params.put("order_by", order.getOrder_by());
        params.put("order_type", order.getOrder_type());

        return myBatisCategoryDao.getCategoriesByCondition(params);
    }
}
