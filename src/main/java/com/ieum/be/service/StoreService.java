package com.ieum.be.service;

import com.ieum.be.dto.CategoryDto;
import com.ieum.be.dto.StoreDto;
import com.ieum.be.dto.StoreInfoDto;
import com.ieum.be.repository.CategoryRepository;
import com.ieum.be.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    public StoreService(StoreRepository storeRepository, CategoryRepository categoryRepository) {
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getCategories() {
        // 카테고리 리스트 반환
        return List.of();
    }

    public List<StoreDto> getStoresByCategory(Long categoryId) {
        // 카테고리에 맞는 음식점 리스트 반환
        return List.of();
    }

    public List<StoreDto> getStoresByOptions(Map<String, String> options) {
        // 옵션을 사용하여 음식점 리스트 반환
        return List.of();
    }

    public StoreInfoDto getStoreDetails(Long restaurantId) {
        // 음식점 상세 정보 반환
        return null;
    }
}
