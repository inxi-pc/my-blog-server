package myblog.resource;

import myblog.model.Category;
import myblog.model.SqlOrder;
import myblog.model.SqlPagination;
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
    public List<Category> getCategories(@QueryParam("limit") int limit,
                                        @QueryParam("offset") int offset,
                                        @QueryParam("orderBy") String orderBy,
                                        @QueryParam("orderType") String orderType) {
        SqlPagination sqlPagination = new SqlPagination(limit, offset);
        SqlOrder sqlOrder = new SqlOrder(orderBy, orderType);

        return CategoryService.getCategories(sqlPagination, sqlOrder);
    }
}
