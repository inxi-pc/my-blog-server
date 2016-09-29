package myblog.dao.MyBatis.Mapper;

import myblog.model.persistent.Category;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {
    /**
     *
     * @param params
     * @return
     */
    List<Category> getCategoriesByCondition(Map<String, Object> params);
}
