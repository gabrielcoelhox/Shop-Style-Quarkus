package com.shopstyle.resources.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CategoryFormDTO {

    @NotNull(message = "name field cannot be null")
    @NotEmpty
    private String name;

    @NotNull
    private boolean active;

    private Long parentId;
}
