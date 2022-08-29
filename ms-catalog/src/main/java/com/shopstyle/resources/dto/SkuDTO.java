package com.shopstyle.resources.dto;

import com.shopstyle.domain.model.Media;
import com.shopstyle.domain.model.Sku;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SkuDTO {

    private Long id;
    private Double price;
    private Integer quantity;
    private String color;
    private String size;
    private Integer height;
    private Integer width;
    private List<Media> images;

    public SkuDTO(Sku sku) {
        this.id = sku.getId();
        this.price = sku.getPrice();
        this.quantity = sku.getQuantity();
        this.color = sku.getColor();
        this.size = sku.getSize();
        this.height = sku.getHeight();
        this.width = sku.getWidth();
        this.images = sku.getImages();
    }
}
