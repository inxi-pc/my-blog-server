package myblog.resource;

import myblog.domain.Category;
import myblog.domain.Pagination;
import myblog.domain.Sort;
import myblog.service.CategoryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/categories")
public class CategoryResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCategory(Category category) {
        int categoryId = CategoryService.createCategory(category);
        if (Category.isValidCategoryId(categoryId)) {
            return Response.created(URI.create("/categories/" + categoryId)).build();
        } else {
            throw new InternalServerErrorException("Unexpected error");
        }
    }

    @DELETE
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory(@PathParam("categoryId") Integer categoryId) {
        if (categoryId == null) {
            throw new BadRequestException("Unexpected category id: Absence value");
        }

        if (Category.isValidCategoryId(categoryId)) {
            if (CategoryService.deleteCategory(categoryId)) {
                return Response.noContent().build();
            } else {
                throw new InternalServerErrorException("Unexpected error");
            }
        } else {
            throw new BadRequestException("Unexpected category id: Invalid value");
        }
    }

    @PUT
    @Path("/{categoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("categoryId") Integer categoryId, Category category) {
        if (categoryId == null) {
            throw new BadRequestException("Unexpected category id: Absence value");
        }

        if (CategoryService.updateCategory(categoryId, category)) {
            return Response.noContent().build();
        } else {
            throw new InternalServerErrorException("Unexpected error");
        }
    }

    @GET
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getCategoryById(@PathParam("categoryId") Integer categoryId) {
        if (categoryId == null) {
            throw new BadRequestException("Unexpected category id: Absence value");
        }

        if (Category.isValidCategoryId(categoryId)) {
            Category category = CategoryService.getCategoryById(categoryId);

            if (category != null) {
                return category;
            } else {
                throw new NotFoundException("Not found category: Id = " + categoryId);
            }
        } else {
            throw new BadRequestException("Unexpected category id: Invalid value");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getCategories(@QueryParam("category_parent_id") Integer categoryParentId,
                                        @QueryParam("category_name") String categoryName,
                                        @QueryParam("category_level") Integer categoryLevel,
                                        @QueryParam("duration_begin") String durationBegin,
                                        @QueryParam("duration_end") String durationEnd,
                                        @QueryParam("category_enabled") Boolean categoryEnabled) {
        Category category = new Category();
        if (categoryParentId != null) {
            category.setCategory_parent_id(categoryParentId);
        }
        if (categoryName != null) {
            category.setCategory_name_en(categoryName);
        }
        if (categoryLevel != null) {
            category.setCategory_level(categoryLevel);
        }
        if (categoryEnabled != null) {
            category.setCategory_enabled(categoryEnabled);
        }

        List<Category> categories = CategoryService.getCategories(category);
        if (categories != null) {
            if (categories.size() > 0) {
                return categories;
            } else {
                throw new NotFoundException("Not found categories");
            }
        } else {
            throw new InternalServerErrorException("Unexpected error");
        }
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Pagination<Category> getCategoryList(@QueryParam("category_parent_id") Integer categoryParentId,
                                                @QueryParam("category_name") String categoryName,
                                                @QueryParam("category_level") Integer categoryLevel,
                                                @QueryParam("duration_begin") String durationBegin,
                                                @QueryParam("duration_end") String durationEnd,
                                                @QueryParam("category_enabled") Boolean categoryEnabled,
                                                @QueryParam("limit") Integer limit,
                                                @QueryParam("offset") Integer offset,
                                                @QueryParam("order_by") String orderBy,
                                                @QueryParam("order_type") String orderType) {
        Category category = new Category();
        if (categoryParentId != null) {
            category.setCategory_parent_id(categoryParentId);
        }
        if (categoryName != null) {
            category.setCategory_name_en(categoryName);
        }
        if (categoryLevel != null) {
            category.setCategory_level(categoryLevel);
        }
        if (categoryEnabled != null) {
            category.setCategory_enabled(categoryEnabled);
        }

        Pagination<Category> page = new Pagination<Category>(limit, offset);
        Sort<Category> order = new Sort<Category>(orderBy, orderType, Category.class);
        page = CategoryService.getCategoryList(category, page, order);

        if (page != null) {
            if (page.getData().size() > 0) {
                return page;
            } else {
                throw new NotFoundException("Not found categories");
            }
        } else {
            throw new InternalServerErrorException("Unexpected error");
        }
    }
}
