package myblog.resource;

import myblog.domain.Category;
import myblog.dao.Pagination;
import myblog.dao.Sort;
import myblog.exception.GenericException;
import myblog.exception.GenericMessageMeta;
import myblog.service.CategoryService;

import javax.annotation.security.PermitAll;
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
            throw new GenericException(GenericMessageMeta.NULL_OBJECT, Category.class, Response.Status.BAD_REQUEST);
        }
        insert.checkFieldOuterSettable();

        int categoryId = CategoryService.createCategory(insert);

        return Response.created(URI.create("/categories/" + categoryId)).build();
    }

    @DELETE
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategory(@PathParam("categoryId") Integer categoryId) {
        if (categoryId == null) {
            throw new GenericException(GenericMessageMeta.NULL_ID, Category.class, Response.Status.BAD_REQUEST);
        }
        if (!Category.isValidCategoryId(categoryId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, Category.class, Response.Status.BAD_REQUEST);
        }

        if (CategoryService.deleteCategory(categoryId)) {
            return Response.noContent().build();
        } else {
            throw new InternalServerErrorException();
        }
    }

    @PUT
    @Path("/{categoryId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCategory(@PathParam("categoryId") Integer categoryId, Category update) {
        if (categoryId == null) {
            throw new GenericException(GenericMessageMeta.NULL_ID, Category.class, Response.Status.BAD_REQUEST);
        }
        if (!Category.isValidCategoryId(categoryId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, Category.class, Response.Status.BAD_REQUEST);
        }
        if (update == null) {
            return Response.noContent().build();
        }

        update.checkFieldOuterSettable();
        if (CategoryService.updateCategory(categoryId, update)) {
            return Response.noContent().build();
        } else {
            throw new InternalServerErrorException();
        }
    }

	@PermitAll
    @GET
    @Path("/{categoryId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getCategoryById(@PathParam("categoryId") Integer categoryId) {
        if (categoryId == null) {
            throw new GenericException(GenericMessageMeta.NULL_ID, Category.class, Response.Status.BAD_REQUEST);
        }
        if (!Category.isValidCategoryId(categoryId)) {
            throw new GenericException(GenericMessageMeta.INVALID_ID, Category.class, Response.Status.BAD_REQUEST);
        }

        Category category = CategoryService.getCategoryById(categoryId);
        if (category != null) {
            return category;
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, Category.class, Response.Status.NOT_FOUND);
        }
    }

	@PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getCategories(@QueryParam("category_parent_id") Integer categoryParentId,
                                        @QueryParam("category_root_id") Integer categoryRootId,
                                        @QueryParam("category_name") String categoryName,
                                        @QueryParam("category_level") Integer categoryLevel,
                                        @QueryParam("category_enabled") Boolean categoryEnabled) {
        Category category = new Category();
        if (categoryParentId != null) {
            category.setCategory_parent_id(categoryParentId);
        }
        if (categoryRootId != null) {
            category.setCategory_root_id(categoryRootId);
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
        if (categories != null && categories.size() > 0) {
            return categories;
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, Category.class, Response.Status.NOT_FOUND);
        }
    }

	@PermitAll
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
            category.setCategory_parent_id(categoryParentId);
        }
        if (categoryRootId != null) {
            category.setCategory_root_id(categoryRootId);
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
        Sort<Category> sort = new Sort<Category>(orderBy, orderType, Category.class);

        if (treeEnabled != null && treeEnabled) {
            page = CategoryService.getCategoryListTree(category, page, sort);
        } else {
            page = CategoryService.getCategoryList(category, page, sort);
        }

        if (page != null && page.getData().size() > 0) {
            return page;
        } else {
            throw new GenericException(GenericMessageMeta.NOT_FOUND_OBJECT, Category.class, Response.Status.NOT_FOUND);
        }
    }
}
