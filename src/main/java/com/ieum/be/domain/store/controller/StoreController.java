package com.ieum.be.domain.store.controller;

import com.ieum.be.domain.store.service.StoreService;
import com.ieum.be.domain.category.dto.CategoryDto;
import com.ieum.be.domain.store.dto.StoreDto;
import com.ieum.be.domain.store.dto.StoreInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(storeService.getCategories());
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<StoreDto>> getRestaurantsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(storeService.getStoresByCategory(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<StoreDto>> getRestaurants(@RequestParam Map<String, String> options) {
        return ResponseEntity.ok(storeService.getStoresByOptions(options));
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreInfoDto> getRestaurantDetails(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.getStoreDetails(storeId));
    }
}
