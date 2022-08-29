package com.shopstyle.resources;

import com.shopstyle.domain.model.Category;
import com.shopstyle.domain.model.Product;
import com.shopstyle.repository.CategoryRepository;
import com.shopstyle.repository.ProductRepository;
import com.shopstyle.resources.dto.ProductDTO;
import com.shopstyle.resources.dto.ProductFormDTO;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/v1/products")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductResource {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Inject
    public ProductResource(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @GET
    public Response findAll() {
        List<ProductDTO> dtoList = productRepository.findAll().list().stream().map(ProductDTO::new).collect(Collectors.toList());
        return Response.ok(dtoList).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Product with id " + id + " not found.").build();
        }
        return Response.ok(new ProductDTO(product)).build();
    }

    @POST
    public Response insert(@Valid ProductFormDTO formDto) {
        Category category = categoryRepository.findById(formDto.getCategoryId());
        if (category == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Category with id " + formDto.getCategoryId() + " not found.").build();
        }
        if(verifyCategory(category)) {
            Product product = new Product(formDto, category);
            productRepository.persist(product);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(new ProductDTO(product)).build();
        } else {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("It is not possible to add a product to this category.").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid ProductFormDTO formDTO) {
        Category category = categoryRepository.findById(formDTO.getCategoryId());
        if (category == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Category with id " + formDTO.getCategoryId() + " not found.").build();
        }
        Product product = productRepository.findById(id);
        if (product == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Product with id " + formDTO.getCategoryId() + " not found.").build();
        }
        if(!verifyCategory(category)) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("It is not possible to add a product to this category.").build();
        } else {
            product.setName(formDTO.getName());
            product.setDescription(formDTO.getDescription());
            product.setActive(formDTO.isActive());
            product.setBrand(formDTO.getBrand());
            product.setMaterial(formDTO.getMaterial());
            product.setCategory(category);
            return Response
                    .status(Response.Status.CREATED)
                    .entity(new ProductDTO(product)).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Product product = productRepository.findById(id);
        if (product == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Product with id " + id + " not found.").build();
        }
        productRepository.deleteById(id);
        return Response.noContent().build();
    }

    private Boolean verifyCategory(Category category) {
        if (!category.getChildren().isEmpty() || !category.isActive()) {
            return false;
        }
        while(category.getParent() != null) {
            if (!category.getParent().isActive()) {
                return false;
            }
            category = category.getParent();
        }
        return true;
    }
}
