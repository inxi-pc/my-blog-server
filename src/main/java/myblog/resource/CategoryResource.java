package myblog.resource;

import myblog.domain.Category;
import myblog.domain.Pagination;
import myblog.domain.Post;
import myblog.domain.Sort;
import myblog.exception.DomainException;
import myblog.exception.HttpExceptionFactory;
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
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    Category.class,
                    HttpExceptionFactory.Reason.ABSENCE_VALUE);
        }

        try {
            insert.checkFieldOuterSettable();
        } catch (DomainException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }

        int categoryId = CategoryService.createCategory(insert);
        if (Category.isValidCategoryId(categoryId)) {
            return Response.created(URI.create("/categories/" + categoryId)).build();
        } else {
            throw HttpExceptionFactory.produce(
                    InternalServerErrorException.class,
                    HttpExceptionFactory.Type.CREATE_FAILED,
                    Category.class,
                    HttpExceptionFactory.Reason.INVALID_PRIMARY_KEY_VALUE);
        }
    }

    @DELETE
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory(@PathParam("categoryId") Integer categoryId) {
        if (categoryId == null) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "categoryId",
                    HttpExceptionFactory.Reason.ABSENCE_VALUE);
        }

        if (Category.isValidCategoryId(categoryId)) {
            if (CategoryService.deleteCategory(categoryId)) {
                return Response.noContent().build();
            } else {
                throw HttpExceptionFactory.produce(
                        InternalServerErrorException.class,
                        HttpExceptionFactory.Type.DELETE_FAILED,
                        Category.class,
                        HttpExceptionFactory.Reason.UNDEFINED_ERROR);
            }
        } else {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "categoryId",
                    HttpExceptionFactory.Reason.INVALID_VALUE);
        }
    }

    @PUT
    @Path("/{categoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("categoryId") Integer categoryId, Category update) {
        if (categoryId == null) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "categoryId",
                    HttpExceptionFactory.Reason.ABSENCE_VALUE);
        }

        if (Category.isValidCategoryId(categoryId)) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "categoryId",
                    HttpExceptionFactory.Reason.INVALID_VALUE);
        }

        if (update == null) {
            return Response.noContent().build();
        }

        try {
            update.checkFieldOuterSettable();
        } catch (DomainException e) {
            throw HttpExceptionFactory.produce(BadRequestException.class, e);
        }

        if (CategoryService.updateCategory(categoryId, update)) {
            return Response.noContent().build();
        } else {
            throw HttpExceptionFactory.produce(
                    InternalServerErrorException.class,
                    HttpExceptionFactory.Type.UPDATE_FAILED,
                    Category.class,
                    HttpExceptionFactory.Reason.UNDEFINED_ERROR);
        }
    }

    @GET
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getCategoryById(@PathParam("categoryId") Integer categoryId) {
        if (categoryId == null) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "categoryId",
                    HttpExceptionFactory.Reason.ABSENCE_VALUE);
        }

        if (!Category.isValidCategoryId(categoryId)) {
            throw HttpExceptionFactory.produce(
                    BadRequestException.class,
                    HttpExceptionFactory.Type.UNEXPECTED,
                    "categoryId",
                    HttpExceptionFactory.Reason.INVALID_VALUE);
        }

        Category category = CategoryService.getCategoryById(categoryId);
        if (category != null) {
            return category;
        } else {
            throw HttpExceptionFactory.produce(
                    NotFoundException.class,
                    HttpExceptionFactory.Type.NOT_FOUND,
                    Category.class,
                    HttpExceptionFactory.Reason.NOT_EXIST);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getCategories(@QueryParam("category_parent_id") Integer categoryParentId,
                                        @QueryParam("category_root_id") Integer categoryRootId,
                                        @QueryParam("category_name") String categoryName,
                                        @QueryParam("category_level") Integer categoryLevel,
                                        @QueryParam("category_enabled") Boolean categoryEnabled) {;
        Category category = new Category();
        if (categoryParentId != null) {
            if (Category.isValidCategoryParentId(categoryParentId)) {
                category.setCategory_parent_id(categoryParentId);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_parent_id",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (categoryRootId != null) {
            if (Category.isValidCategoryRootId(categoryRootId)) {
                category.setCategory_root_id(categoryRootId);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_root_id",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (categoryName != null) {
            if (Category.isValidCategoryName(categoryName)) {
                category.setCategory_name_en(categoryName);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_name",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (categoryLevel != null) {
            if (Category.isValidCategoryLevel(categoryLevel)) {
                category.setCategory_level(categoryLevel);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_level",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (categoryEnabled != null) {
            if (Category.isValidCategoryEnabled(categoryEnabled)) {
                category.setCategory_enabled(categoryEnabled);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_enabled",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }

        List<Category> categories = CategoryService.getCategories(category);
        if (categories != null && categories.size() > 0) {
            return categories;
        } else {
            throw HttpExceptionFactory.produce(
                    NotFoundException.class,
                    HttpExceptionFactory.Type.NOT_FOUND,
                    Category.class,
                    HttpExceptionFactory.Reason.NOT_ELIGIBLE);
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
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_parent_id",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (categoryRootId != null) {
            if (Category.isValidCategoryRootId(categoryRootId)) {
                category.setCategory_root_id(categoryRootId);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_root_id",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (categoryName != null) {
            if (Category.isValidCategoryName(categoryName)) {
                category.setCategory_name_en(categoryName);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_name",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (categoryLevel != null) {
            if (Category.isValidCategoryLevel(categoryLevel)) {
                category.setCategory_level(categoryLevel);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_level",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }
        if (categoryEnabled != null) {
            if (Category.isValidCategoryEnabled(categoryEnabled)) {
                category.setCategory_enabled(categoryEnabled);
            } else {
                throw HttpExceptionFactory.produce(
                        BadRequestException.class,
                        HttpExceptionFactory.Type.UNEXPECTED,
                        "category_enbabled",
                        HttpExceptionFactory.Reason.INVALID_VALUE);
            }
        }

        Pagination<Category> page = new Pagination<Category>(limit, offset);
        Sort<Category> sort = new Sort<Category>(orderBy, orderType, Category.class);

        if (treeEnabled != null && treeEnabled) {
            page = CategoryService.getCategoryListTree(category, page, sort);
        } else {
            page = CategoryService.getCategoryList(category, page, sort);
        }

        if (page != null && page.getData().size() > 0) {
            return page;
        } else {
            throw HttpExceptionFactory.produce(
                    NotFoundException.class,
                    HttpExceptionFactory.Type.NOT_FOUND,
                    Post.class,
                    HttpExceptionFactory.Reason.NOT_ELIGIBLE);
        }
    }
}
