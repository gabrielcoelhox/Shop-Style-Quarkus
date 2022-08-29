package com.shopstyle.resources;

import com.shopstyle.domain.model.Media;
import com.shopstyle.domain.model.Product;
import com.shopstyle.domain.model.Sku;
import com.shopstyle.repository.MediaRepository;
import com.shopstyle.repository.ProductRepository;
import com.shopstyle.repository.SkuRepository;
import com.shopstyle.resources.dto.SkuDTO;
import com.shopstyle.resources.dto.SkuFormDTO;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/skus")
@Transactional
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SkuResource {

    private SkuRepository skuRepository;
    private ProductRepository productRepository;
    private MediaRepository mediaRepository;

    @Inject
    public SkuResource(SkuRepository skuRepository, ProductRepository productRepository, MediaRepository mediaRepository) {
        this.skuRepository = skuRepository;
        this.productRepository = productRepository;
        this.mediaRepository = mediaRepository;
    }

    @POST
    public Response insert(@Valid SkuFormDTO formDTO) {
        Product product = productRepository.findById(formDTO.getProductId());
        if (product == null){
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Product with id " + formDTO.getProductId() + " not found.")
                    .build();
        }
        Sku sku = new Sku(formDTO, product);
        for(String imagemUrl : formDTO.getImages()) {
            Media media = new Media(imagemUrl, sku);
            sku.addImages(media);
            mediaRepository.persist(media);
        }
        skuRepository.persist(sku);
        return Response
                .status(Response.Status.CREATED)
                .entity(new SkuDTO(sku))
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid SkuFormDTO formDTO) {
        Sku sku = skuRepository.findById(id);
        if (sku == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Sku with id: " + id + " not found.").build();
        }
        Product product = productRepository.findById(formDTO.getProductId());
        if (product == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Product with id: " + formDTO.getProductId() + " not found.")
                    .build();
        }
        sku.setProduct(product);
        sku.setColor(formDTO.getColor());
        sku.setPrice(formDTO.getPrice());
        sku.setQuantity(formDTO.getQuantity());
        sku.setSize(formDTO.getSize());
        sku.setHeight(formDTO.getHeight());
        sku.setWidth(formDTO.getWidth());

        for(String imagemUrl : formDTO.getImages()) {
            mediaRepository.persist(new Media(imagemUrl, sku));
        }
        return Response.ok(new SkuDTO(sku)).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        if (skuRepository.findById(id) == null) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Sku with id: " + id + " not found.")
                    .build();
        }
        skuRepository.deleteById(id);
        return Response.noContent().build();
    }
}
