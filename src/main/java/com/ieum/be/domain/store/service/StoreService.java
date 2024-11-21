package com.ieum.be.domain.store.service;

import com.ieum.be.domain.category.entity.Category;
import com.ieum.be.domain.category.repository.CategoryRepository;
import com.ieum.be.domain.store.dto.StoreDto;
import com.ieum.be.domain.store.dto.StoreInfoDto;
import com.ieum.be.domain.store.entity.Store;
import com.ieum.be.domain.store.repository.StoreRepository;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private CategoryRepository categoryRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    // excel 파일로부터 결식아동카드 음식점 dataset 가져오기
    @Transactional
    public void importStoresFromExcel(String filePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Header 행 제외
            Row row = sheet.getRow(i);

            String storeName = row.getCell(0).getStringCellValue();
            String roadAddress = row.getCell(1).getStringCellValue();
            Float latitude = (float) row.getCell(2).getNumericCellValue();
            Float longitude = (float) row.getCell(3).getNumericCellValue();
            String phone = row.getCell(4) != null ? row.getCell(4).getStringCellValue() : null;
            String categoryName = row.getCell(5).getStringCellValue();
            Float score = (float) row.getCell(6).getNumericCellValue();
            String imageUrl = row.getCell(7).getStringCellValue();

            // 카테고리 가져오기 또는 생성
            Category category = categoryRepository.findByCategoryName(categoryName)
                    .orElseGet(() -> {
                        Category newCategory = new Category();
                        newCategory.setCategoryName(categoryName);
                        return categoryRepository.save(newCategory);
                    });

            // Store entity 생성
            Store store = new Store(storeName, roadAddress, phone, latitude, longitude, score, imageUrl, category);
            storeRepository.save(store);
        }

        workbook.close();
        fileInputStream.close();
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
