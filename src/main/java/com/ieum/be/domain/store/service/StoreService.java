package com.ieum.be.domain.store.service;

import com.ieum.be.domain.store.dto.StoreDto;
import com.ieum.be.domain.store.dto.StoreInfoDto;
import com.ieum.be.domain.store.entity.Store;
import com.ieum.be.domain.store.repository.StoreRepository;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {
    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreDto> getStoresByCategoryId(Integer categoryId) {
        // 카테고리에 맞는 음식점 리스트 반환
        List<Store> stores = this.storeRepository.findByCategory_CategoryId(categoryId);
        return stores.stream()
                .map(store -> new StoreDto(
                        store.getStoreId(),
                        store.getStoreName(),
                        store.getRoadAddress(),
                        store.getLatitude(),
                        store.getLongitude()
                ))
                .toList();
    }

    public List<StoreDto> getStoresByOptions(String options) {
        // 옵션을 사용하여 음식점 리스트 반환
        throw new UnsupportedOperationException("This feature is not yet implemented");
    }

    public StoreInfoDto getStoreById(Integer storeId) {
        // 음식점 상세 정보 반환
        Store store = this.storeRepository.findByStoreId(storeId)
                .orElseThrow(() -> new GlobalException(GeneralResponse.NOT_FOUND));

        return new StoreInfoDto(
                store.getStoreId(),
                store.getStoreName(),
                store.getRoadAddress(),
                store.getPhone(),
                store.getImageUrl());
    }
}
