package com.ieum.be.domain.store.controller;

import com.ieum.be.domain.store.service.StoreService;
import com.ieum.be.domain.store.dto.StoreDto;
import com.ieum.be.domain.store.dto.StoreInfoDto;
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

    // 카테고리별 음식점 리스트 조회
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getStoresByCategoryId(@PathVariable Integer categoryId) {
        List<StoreDto> stores = storeService.getStoresByCategoryId(categoryId);
        return ResponseEntity.ok(stores);
    }

    // 옵션별 음식점 리스트 조회
    @GetMapping
    public ResponseEntity<?> getStoresByOptions(@RequestParam(required = false) String options) {
        List<StoreDto> stores = storeService.getStoresByOptions(options);
        return ResponseEntity.ok(stores);
    }

    // 음식점 상세 정보 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<?> getStoreById(@PathVariable Integer storeId) {
        StoreInfoDto storeInfo = storeService.getStoreById(storeId);
        return ResponseEntity.ok(storeInfo);
    }
}
