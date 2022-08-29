package com.shopstyle.resources;

import com.shopstyle.domain.model.Category;
import com.shopstyle.repository.CategoryRepository;
import com.shopstyle.resources.dto.CategoryDTO;
import com.shopstyle.resources.dto.CategoryFormDTO;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1/categories")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

    private CategoryRepository categoryRepository;

    @Inject
    public CategoryResource(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GET
    public Response findAll() {
        List<Category> list = categoryRepository.findAll().list();
        List<CategoryDTO> dtoList = list.stream().map(CategoryDTO::new).collect(Collectors.toList());
        return Response.ok(dtoList).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Category category = categoryRepository.findById(id);
        if(category == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Category with id " + id + " not found.")
                    .build();
        }
        return Response.ok(new CategoryDTO(category)).build();
    }

    @POST
    public Response insert(@Valid CategoryFormDTO formDTO) {

        if(formDTO.getParentId() == null) {
            Category category = new Category(formDTO);
            categoryRepository.persist(category);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(new CategoryDTO(category))
                    .build();
        }
        Category parentCategory = categoryRepository.findById(formDTO.getParentId());
        if(parentCategory == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("ParentCategory with id: " + formDTO.getParentId() + " not found.")
                    .build();
        } else {
            Category saveCategory = new Category(formDTO, parentCategory);
            categoryRepository.persist(saveCategory);
            parentCategory.addChildren(saveCategory);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(new CategoryDTO(saveCategory))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid CategoryFormDTO formDTO) {
        Category category = categoryRepository.findById(id);
        if(category == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Category id with " + id + " not found.")
                    .build();
        }
        category.setName(formDTO.getName());
        category.setActive(formDTO.isActive());
        return Response
                .status(Response.Status.OK)
                .entity(new CategoryDTO(category))
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Category category = categoryRepository.findById(id);
        if(category == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Category with id " + id + " not found.")
                    .build();
        }
        categoryRepository.deleteById(id);
        return Response.noContent().build();
    }
}
