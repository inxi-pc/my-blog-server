package myblog.resource;

import myblog.model.persistent.Category;
import myblog.model.business.OrderBo;
import myblog.model.business.PaginationBo;
import myblog.service.CategoryService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/categories")
public class CategoryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getCategoryList(@QueryParam("limit") int limit,
                                          @QueryParam("offset") int offset,
                                          @QueryParam("order_by") String orderBy,
                                          @QueryParam("order_type") String orderType) {
        PaginationBo page = new PaginationBo(limit, offset);
        OrderBo order = new OrderBo(orderBy, orderType);

        return CategoryService.getCategoryList(page, order);
    }
}
