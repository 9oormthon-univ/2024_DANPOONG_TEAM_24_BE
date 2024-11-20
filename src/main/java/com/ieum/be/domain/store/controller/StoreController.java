package com.ieum.be.domain.store.controller;

import com.ieum.be.domain.store.service.StoreService;
import com.ieum.be.domain.store.dto.StoreDto;
import com.ieum.be.domain.store.dto.StoreInfoDto;
import com.ieum.be.global.response.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getStoresByCategoryId(@PathVariable Integer categoryId) {
        List<StoreDto> stores = storeService.getStoresByCategoryId(categoryId);
        return ResponseEntity.ok(stores);
    }

    /*@GetMapping
    public ResponseEntity<?> getStoresByOptions(@RequestParam(required = false) String options) {
        try {
            List<StoreDto> stores = storeService.getStoresByOptions(options);
            return ResponseEntity.ok(new GeneralResponse<>(HttpStatus.OK.value(), stores));
        } catch (UnsupportedOperationException e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new GeneralResponse<>(HttpStatus.NOT_IMPLEMENTED.value(), e.getMessage()));
        }
    }*/

    @GetMapping("/{storeId}")
    public ResponseEntity<?> getStoreById(@PathVariable Integer storeId) {
        StoreInfoDto storeInfo = storeService.getStoreById(storeId);
        return ResponseEntity.ok(storeInfo);
    }
}
