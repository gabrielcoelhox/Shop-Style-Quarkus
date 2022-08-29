package com.shopstyle.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopstyle.resources.dto.SkuFormDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Sku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Price field cannot be null")
    private Double price;

    @NotNull (message = "Quantity field cannot be null")
    private Integer quantity;

    @NotNull (message = "Color field cannot be null")
    @NotEmpty
    private String color;

    @NotNull (message = "Size field cannot be null")
    @NotEmpty
    private String size;

    @NotNull (message = "Height field cannot be null")
    private Integer height;

    @NotNull (message = "Width field cannot be null")
    private Integer width;

    @NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "sku", cascade = CascadeType.ALL)
    @NotNull @NotEmpty
    private List<Media> images = new ArrayList<>();

    public void addImages(Media media) {
        this.images.add(media);
    }

    public Sku(SkuFormDTO form, Product product) {
        this.price = form.getPrice();
        this.quantity = form.getQuantity();
        this.color = form.getColor();
        this.size = form.getSize();
        this.height = form.getHeight();
        this.width = form.getWidth();
        this.product = product;
    }
}
