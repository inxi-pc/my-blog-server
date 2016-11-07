package myblog.resource;

import myblog.domain.Category;
import myblog.domain.Pagination;
import myblog.domain.Sort;
import myblog.exception.DomainException;
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
    public Response createCategory(Category insert) {
        if (insert == null) {
            throw new BadRequestException("Unexpected category: Absence value");
        }

        try {
            insert.checkFieldOuterSettable();
        } catch (DomainException e) {
            throw new BadRequestException(e.getMessage(), e);
        }

        int categoryId = CategoryService.createCategory(insert);

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
    public Response updateCategory(@PathParam("categoryId") Integer categoryId, Category update) {
        if (categoryId == null) {
            throw new BadRequestException("Unexpected category id: Absence value");
        }

        if (update == null) {
            return Response.noContent().build();
        }

        try {
            update.checkFieldOuterSettable();
        } catch (DomainException e) {
            throw new BadRequestException(e.getMessage(), e);
        }

        if (Category.isValidCategoryId(categoryId)) {
            if (CategoryService.updateCategory(categoryId, update)) {
                return Response.noContent().build();
            } else {
                throw new InternalServerErrorException("Unexpected error");
            }
        } else {
            throw new BadRequestException("Unexpected category id: Invalid value");
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
                                        @QueryParam("category_root_id") Integer categoryRootId,
                                        @QueryParam("category_name") String categoryName,
                                        @QueryParam("category_level") Integer categoryLevel,
                                        @QueryParam("category_enabled") Boolean categoryEnabled) {
        boolean noQueryParam = true;
        Category category = new Category();
        if (categoryParentId != null) {
            if (Category.isValidCategoryParentId(categoryParentId)) {
                noQueryParam = false;
                category.setCategory_parent_id(categoryParentId);
            } else {
                throw new BadRequestException("Unexpected category parent id: Invalid value");
            }
        }
        if (categoryRootId != null) {
            if (Category.isValidCategoryRootId(categoryRootId)) {
                noQueryParam = false;
                category.setCategory_root_id(categoryRootId);
            } else {
                throw new BadRequestException("Unexpected category root id: Invalid value");
            }
        }
        if (categoryName != null) {
            if (Category.isValidCategoryName(categoryName)) {
                noQueryParam = false;
                category.setCategory_name_en(categoryName);
            } else {
                throw new BadRequestException("Unexpected category name: Invalid value");
            }
        }
        if (categoryLevel != null) {
            if (Category.isValidCategoryLevel(categoryLevel)) {
                noQueryParam = false;
                category.setCategory_level(categoryLevel);
            } else {
                throw new BadRequestException("Unexpected category level: Invalid value");
            }
        }
        if (categoryEnabled != null) {
            if (Category.isValidCategoryEnabled(categoryEnabled)) {
                noQueryParam = false;
                category.setCategory_enabled(categoryEnabled);
            } else {
                throw new BadRequestException("Unexpected category enabled: Invalid value");
            }
        }
        if (noQueryParam) {
            throw new BadRequestException("Unexpected query parameter: No query parameter");
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
                                                @QueryParam("category_root_id") Integer categoryRootId,
                                                @QueryParam("category_name") String categoryName,
                                                @QueryParam("category_level") Integer categoryLevel,
                                                @QueryParam("category_enabled") Boolean categoryEnabled,
                                                @QueryParam("limit") Integer limit,
                                                @QueryParam("offset") Integer offset,
                                                @QueryParam("order_by") String orderBy,
                                                @QueryParam("order_type") String orderType,
                                                @QueryParam("tree_enabled") Boolean treeEnabled) {
        Category category = new Category();
        if (categoryParentId != null) {
            if (Category.isValidCategoryParentId(categoryParentId)) {
                category.setCategory_parent_id(categoryParentId);
            } else {
                throw new BadRequestException("Unexpected category parent id: Invalid value");
            }
        }
        if (categoryRootId != null) {
            if (Category.isValidCategoryRootId(categoryRootId)) {
                category.setCategory_root_id(categoryRootId);
            } else {
                throw new BadRequestException("Unexpected category root id: Invalid value");
            }
        }
        if (categoryName != null) {
            if (Category.isValidCategoryName(categoryName)) {
                category.setCategory_name_en(categoryName);
            } else {
                throw new BadRequestException("Unexpected category name: Invalid value");
            }
        }
        if (categoryLevel != null) {
            if (Category.isValidCategoryLevel(categoryLevel)) {
                category.setCategory_level(categoryLevel);
            } else {
                throw new BadRequestException("Unexpected category level: Invalid value");
            }
        }
        if (categoryEnabled != null) {
            if (Category.isValidCategoryEnabled(categoryEnabled)) {
                category.setCategory_enabled(categoryEnabled);
            } else {
                throw new BadRequestException("Unexpected category enabled: Invalid value");
            }
        }

        Pagination<Category> page = new Pagination<Category>(limit, offset);
        Sort<Category> sort = new Sort<Category>(orderBy, orderType, Category.class);

        if (treeEnabled != null && treeEnabled) {
            page = CategoryService.getCategoryListTree(category, page, sort);
        } else {
            page = CategoryService.getCategoryList(category, page, sort);
        }

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
