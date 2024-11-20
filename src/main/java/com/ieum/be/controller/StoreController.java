package com.ieum.be.controller;

import com.ieum.be.dto.CategoryDto;
import com.ieum.be.dto.StoreDto;
import com.ieum.be.dto.StoreInfoDto;
import com.ieum.be.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
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
