package com.ieum.be.domain.store.service;

import com.ieum.be.domain.category.entity.Category;
import com.ieum.be.domain.category.repository.CategoryRepository;
import com.ieum.be.domain.store.dto.StoreDto;
import com.ieum.be.domain.store.dto.StoreInfoDto;
import com.ieum.be.domain.store.entity.Store;
import com.ieum.be.domain.store.repository.StoreRepository;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    public StoreService(StoreRepository storeRepository, CategoryRepository categoryRepository) {
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
    }

    // 음식점 상세 정보 반환
    public StoreInfoDto getStoreById(Integer storeId) {
        Store store = this.storeRepository.findByStoreId(storeId)
                .orElseThrow(() -> new GlobalException(GeneralResponse.NOT_FOUND));

        return new StoreInfoDto(
                store.getStoreId(),
                store.getStoreName(),
                store.getRoadAddress(),
                store.getPhone(),
                store.getImageUrl(),
                store.getScore(),
                store.getAveragePrice());
    }

    // 반경 1km 내 카테고리별 식당 반환
    public List<StoreDto> getStoresByCategoryAndLocation(Integer categoryId, Double latitude, Double longitude) {
        List<Store> stores = storeRepository.findStoresByCategoryAndDistance(categoryId, latitude, longitude, 2000);
        return stores.stream()
                .map(store -> new StoreDto(
                        store.getStoreId(),
                        store.getStoreName(),
                        store.getRoadAddress(),
                        store.getLatitude(),
                        store.getLongitude(),
                        store.getPhone(),
                        store.getImageUrl(),
                        store.getKakaoMapUrl(),
                        store.getAveragePrice()
                ))
                .toList();
    }

    // 반경 1km 내 옵션별 식당 반환
    public List<StoreDto> getStoresByOptionsAndLocation(String options, Double latitude, Double longitude) {
        List<Store> stores;

        if (options != null && options.equalsIgnoreCase("score>=4")) {
            // 옵션이 "score>=4"인 경우
            stores = storeRepository.findByScoreAndDistance(4, latitude, longitude, 2000);
        } else {
            // 옵션이 없을 경우, 모든 반경 1km 내의 식당 반환
            stores = storeRepository.findStoresByDistance(latitude, longitude, 2000);
        }

        return stores.stream()
                .map(store -> new StoreDto(
                        store.getStoreId(),
                        store.getStoreName(),
                        store.getRoadAddress(),
                        store.getLatitude(),
                        store.getLongitude(),
                        store.getPhone(),
                        store.getImageUrl(),
                        store.getKakaoMapUrl(),
                        store.getAveragePrice()
                ))
                .collect(Collectors.toList());
    }
}
