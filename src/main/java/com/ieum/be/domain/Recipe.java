package com.ieum.be.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Integer recipeId;

    @Column(name = "recipe_name", nullable = false)
    private String recipeName;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price_range")
    private String priceRange;

    @Column(name = "keywords", columnDefinition = "json")
    private String keywords;

    @Enumerated(EnumType.STRING)
    @Column(name = "convenience_store", nullable = false)
    private ConvenienceStoreType convenienceStore;

    public Recipe(Integer recipeId, String recipeName, String description, String priceRange, String keywords, ConvenienceStoreType convenienceStore) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.description = description;
        this.priceRange = priceRange;
        this.keywords = keywords;
        this.convenienceStore = convenienceStore;
    }

    public Recipe() {}
}

enum ConvenienceStoreType {
    GS25, CU, SEVEN_ELEVEN, OTHER
}
