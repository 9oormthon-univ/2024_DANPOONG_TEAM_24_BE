package com.ieum.be.domain.category.dto;

public class CategoryDto {
    private Integer categoryId;
    private String categoryName;

    public CategoryDto(Integer categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public CategoryDto() {}
}

