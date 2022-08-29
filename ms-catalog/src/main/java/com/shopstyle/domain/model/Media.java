package com.shopstyle.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String imageUrl;

    @ManyToOne
    @JsonIgnore
    private Sku sku;

    public Media(@NotNull @NotEmpty String imageUrl, Sku sku) {
        this.imageUrl = imageUrl;
        this.sku = sku;
    }
}
