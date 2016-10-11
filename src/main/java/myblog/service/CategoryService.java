package myblog.service;

import myblog.dao.DaoFactory;
import myblog.dao.MyBatis.CategoryDaoMyBatisImpl;
import myblog.domain.Category;
import myblog.domain.Sort;
import myblog.domain.Pagination;

import java.util.HashMap;
import java.util.List;

public class CategoryService {

    public static Pagination<Category> getCategoryList(Pagination<Category> page, Sort order) {
        CategoryDaoMyBatisImpl myBatisCategoryDao = (CategoryDaoMyBatisImpl)
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
