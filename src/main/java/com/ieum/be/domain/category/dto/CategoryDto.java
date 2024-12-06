package com.ieum.be.domain.category.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    private String categoryName;

    public CategoryDto(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}

