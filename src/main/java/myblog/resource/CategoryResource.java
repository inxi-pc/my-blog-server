package myblog.resource;

import myblog.domain.Category;
import myblog.domain.Pagination;
import myblog.domain.Sort;
import myblog.service.CategoryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/categories")
public class CategoryResource {

//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public int createCategory(Category category) {
//        if (category != null && !category.checkAllFieldsIsNullExceptPK()) {
//
//        } else {
//            throw new BadRequestException();
//        }
//    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Pagination<Category> getCategoryList(@QueryParam("category_name") String categoryName,
                                                @QueryParam("category_level") Integer categoryLevel,
                                                @QueryParam("duration_start") String durationStart,
                                                @QueryParam("duration_end") String durationEnd,
                                                @QueryParam("category_enabled") Boolean categoryEnabled,
                                                @QueryParam("limit") Integer limit,
                                                @QueryParam("offset") Integer offset,
                                                @QueryParam("order_by") String orderBy,
                                                @QueryParam("order_type") String orderType) {
        Category category = new Category();
        if (Category.isValidCategoryName(categoryName)) {
            category.setCategory_name_en(categoryName);
        }
        if (Category.isValidCategoryLevel(categoryLevel)) {
            category.setCategory_level(categoryLevel);
        }
        if (Category.isValidDurationBegin(durationStart)) {
            category.setDuration_start(durationStart);
        }
        if (Category.isValidDurationEnd(durationEnd)) {
            category.setDuration_end(durationEnd);
        }
        if (Category.isValidCategoryEnabled(categoryEnabled)) {
            category.setCategory_enabled(categoryEnabled);
        }
        Pagination<Category> page = new Pagination<Category>(limit, offset);
        Sort<Category> order = new Sort<Category>(orderBy, orderType, Category.class);
        page = CategoryService.getCategoryList(category, page, order);

        if (page != null) {
            if (page.getData().size() > 0) {
                return page;
            } else {
                throw new NotFoundException();
            }
        } else {
            throw new InternalServerErrorException();
        }
    }
}
