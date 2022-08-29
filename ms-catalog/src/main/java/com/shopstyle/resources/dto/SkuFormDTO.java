package com.shopstyle.resources.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class SkuFormDTO {

    @NotNull(message = "Price field cannot be null")
    private Double price;

    @NotNull (message = "Quantity field cannot be null")
    private Integer quantity;

    @NotNull(message = "Color field cannot be null")
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
    private Long productId;

    @NotNull @NotEmpty
    private List<String> images;
}
