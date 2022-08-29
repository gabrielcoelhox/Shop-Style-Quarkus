package com.shopstyle.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shopstyle.resources.dto.CategoryFormDTO;
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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name field cannot be null")
    @NotEmpty
    private String name;

    @NotNull(message = "Category field cannot be null")
    private boolean active;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Category> children = new ArrayList<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private List<Product> products;

    public void addChildren(Category category) {
        this.children.add(category);
    }

    public Category(CategoryFormDTO category, Category parentCategory) {
        this.name = category.getName();
        this.active = category.isActive();
        this.parent = parentCategory;
    }

    public Category(CategoryFormDTO category) {
        this.name = category.getName();
        this.active = category.isActive();
    }
}
