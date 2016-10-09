package myblog.resource;

import myblog.model.persistence.Category;
import myblog.model.persistence.Order;
import myblog.model.persistence.Pagination;
import myblog.service.CategoryService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/categories")
public class CategoryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Pagination<Category> getCategoryList(@QueryParam("limit") int limit,
                                                @QueryParam("offset") int offset,
                                                @QueryParam("order_by") String orderBy,
                                                @QueryParam("order_type") String orderType) {
        Pagination<Category> page = new Pagination<Category>(limit, offset);
        Order<Category> order = new Order<Category>(orderBy, orderType, Category.class);

        return CategoryService.getCategoryList(page, order);
    }
}
