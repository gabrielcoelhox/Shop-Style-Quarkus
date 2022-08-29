package com.shopstyle.resources.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ProductFormDTO {

    @NotNull(message = "name field cannot be null")
    @NotEmpty
    private String name;

    @NotNull @NotEmpty
    private String description;

    @NotNull (message = "brand field cannot be null")
    @NotEmpty
    private String brand;

    private String material;

    @NotNull
    private boolean active;

    @NotNull
    private Long categoryId;
}
