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

    // 카테고리별 음식점 리스트 조회 (반경 1km 내)
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getStoresByCategoryAndLocation(
            @PathVariable Integer categoryId,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        List<StoreDto> stores = storeService.getStoresByCategoryAndLocation(categoryId, latitude, longitude);
        return ResponseEntity.ok(stores);
    }

    // 옵션별 음식점 리스트 조회 (반경 1km 내)
    @GetMapping
    public ResponseEntity<?> getStoresByOptionsAndLocation(
            @RequestParam(required = false) String options,
            @RequestParam Double latitude,
            @RequestParam Double longitude) {
        List<StoreDto> stores = storeService.getStoresByOptionsAndLocation(options, latitude, longitude);
        return ResponseEntity.ok(stores);
    }

    // 음식점 상세 정보 조회
    @GetMapping("/{storeId}")
    public ResponseEntity<?> getStoreById(@PathVariable Integer storeId) {
        StoreInfoDto storeInfo = storeService.getStoreById(storeId);
        return ResponseEntity.ok(storeInfo);
    }
}
