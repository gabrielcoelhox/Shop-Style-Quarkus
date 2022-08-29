package com.shopstyle.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopstyle.resources.dto.ProductFormDTO;
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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name field cannot be null")
    @NotEmpty(message = "Name field cannot be empty")
    private String name;

    @NotNull (message = "Description field cannot be null")
    @NotEmpty (message = "Description field cannot be empty")
    private String description;

    @NotNull (message = "Brand field cannot be null")
    @NotEmpty (message = "Brand field cannot be empty")
    private String brand;

    private String material;

    @NotNull
    private boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<Sku> skus = new ArrayList<>();

    @ManyToOne
    @NotNull
    @JoinColumn(name = "category_id")
    private Category category;

    public Product(ProductFormDTO form, Category category) {
        this.name = form.getName();
        this.description = form.getDescription();
        this.brand = form.getBrand();
        this.material = form.getMaterial();
        this.active = form.isActive();
        this.category = category;
    }
}
