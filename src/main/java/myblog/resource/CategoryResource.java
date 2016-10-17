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
        if (category.getCategory_name_cn() != null) {
            if (!Category.isValidCategoryName(category.getCategory_name_cn())) {
                throw new BadRequestException("Unexpected category name cn");
            }
        }
        if (category.getCategory_name_en() != null) {
            if (!Category.isValidCategoryName(category.getCategory_name_en())) {
                throw new BadRequestException("Unexpected category name en");
            }
        }
        category.setCategory_enabled(true);
        category.setCategory_created_at(null);
        category.setCategory_updated_at(null);

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
        if (categoryId != null && Category.isValidCategoryId(categoryId)) {
            if (CategoryService.getCategoryById(categoryId) != null) {
                if (CategoryService.deleteCategory(categoryId)) {
                    return Response.noContent().build();
                } else {
                    throw new InternalServerErrorException();
                }
            } else {
                throw new NotFoundException("Not found category");
            }
        } else {
            throw new BadRequestException("Unexpected category id");
        }
    }

    @PUT
    @Path("/{categoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("categoryId") Integer categoryId, Category category) {
        if (categoryId == null || !Category.isValidCategoryId(categoryId)) {
            throw new BadRequestException("Unexpected category id");
        }
        if (category.getCategory_name_cn() != null) {
            if (!Category.isValidCategoryName(category.getCategory_name_cn())) {
                throw new BadRequestException("Unexpected category name cn");
            }
        }
        if (category.getCategory_name_en() != null) {
            if (!Category.isValidCategoryName(category.getCategory_name_en())) {
                throw new BadRequestException("Unexpected category name en");
            }
        }
        category.setCategory_updated_at(null);

        if (CategoryService.getCategoryById(categoryId) != null) {
            category.setCategory_id(categoryId);
            if (CategoryService.updateCategory(category)) {
                return Response.noContent().build();
            } else {
                throw new InternalServerErrorException();
            }
        } else {
            throw new NotFoundException("Not found category");
        }
    }

    @GET
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getCategoryById(@PathParam("categoryId") Integer categoryId) {
        if (categoryId != null && Category.isValidCategoryId(categoryId)) {
            Category category = CategoryService.getCategoryById(categoryId);

            if (category != null) {
                return category;
            } else {
                throw new NotFoundException("Not found category");
            }
        } else {
            throw new BadRequestException("Unexpected category id");
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
        if (durationBegin != null) {
            category.setDuration_begin(durationBegin);
        }
        if (durationEnd != null) {
            category.setDuration_end(durationEnd);
        }
        if (categoryEnabled != null) {
            category.setCategory_enabled(categoryEnabled);
        }

        if (!category.checkAllFieldsIsNull()) {
            List<Category> categories = CategoryService.getCategories(category);
            if (categories != null) {
                if (categories.size() > 0) {
                    return categories;
                } else {
                    throw new NotFoundException("Not found categories");
                }
            } else {
                throw new InternalServerErrorException();
            }
        } else {
            throw new BadRequestException("Unexpected category");
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
        if (durationBegin != null) {
            category.setDuration_begin(durationBegin);
        }
        if (durationEnd != null) {
            category.setDuration_end(durationEnd);
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
            throw new InternalServerErrorException();
        }
    }
}
