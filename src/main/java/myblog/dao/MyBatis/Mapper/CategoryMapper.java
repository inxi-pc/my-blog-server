package myblog.dao.MyBatis.Mapper;

import myblog.model.Category;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {
    /**
     *
     * @param params
     * @return
     */
    List<Category> getCategoryListByCondition(Map<String, Object> params);
}
