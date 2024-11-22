package com.ieum.be.domain.store.service;

import com.ieum.be.domain.category.entity.Category;
import com.ieum.be.domain.category.repository.CategoryRepository;
import com.ieum.be.domain.store.dto.StoreDto;
import com.ieum.be.domain.store.dto.StoreInfoDto;
import com.ieum.be.domain.store.entity.Store;
import com.ieum.be.domain.store.repository.StoreRepository;
import com.ieum.be.global.response.GeneralResponse;
import com.ieum.be.global.response.GlobalException;
import jakarta.annotation.PostConstruct;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {

    @Autowired
    private ResourceLoader resourceLoader;

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    public StoreService(StoreRepository storeRepository, CategoryRepository categoryRepository) {
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostConstruct
    public void init() {
        loadExcelFileIfNeeded();
    }

    // DB에 데이터가 있는지 확인
    public void loadExcelFileIfNeeded() {
        long storeCount = storeRepository.count();

        if (storeCount == 0) { // 데이터가 비어 있을 경우에만 엑셀 파일 로드
            System.out.println("No store data found. Loading from Excel...");
            loadExcelFile();
        } else {
            System.out.println("Store data already exists. Skipping Excel loading.");
        }
    }

    // resources 디렉토리의 파일 로드
    public void loadExcelFile() {
        try {
            Resource resource = resourceLoader.getResource("classpath:data/store/아동급식카드 가맹점.xlsx");
            File file = resource.getFile(); // 파일 객체 생성
            String filepath = file.getAbsolutePath();

            System.out.println("Excel file path: " + filepath);

            // 이후 파일 처리 로직
            processExcelFile(filepath);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load Excel file from resources", e);
        }
    }

    // excel 파일로부터 아동급식카드 가맹점 dataset 가져오기
    @Transactional
    public void processExcelFile(String filePath) throws IOException {
        // 엑셀 파일 로드
        FileInputStream fileInputStream = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Header 행 제외
            Row row = sheet.getRow(i);
            if (row == null) continue; // 빈 행 스킵

            // 셀 값 가져오기 (null 체크 포함)
            String storeName = getCellValueAsString(row.getCell(0));
            String roadAddress = getCellValueAsString(row.getCell(1));
            Float latitude = getCellValueAsFloat(row.getCell(2));
            Float longitude = getCellValueAsFloat(row.getCell(3));
            String phone = getCellValueAsString(row.getCell(4));
            String categoryName = getCellValueAsString(row.getCell(5));
            Float score = getCellValueAsFloat(row.getCell(6));
            String imageUrl = getCellValueAsString(row.getCell(7));

            if (storeName == null || roadAddress == null || categoryName == null) {
                // 필수 값이 없으면 스킵
                continue;
            }

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

    // 셀 값을 문자열로 변환
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BLANK:
            case _NONE:
                return null;
            default:
                return cell.toString();
        }
    }

    // 셀 값을 Float로 변환
    private Float getCellValueAsFloat(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return (float) cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return Float.parseFloat(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return null; // 변환 실패 시 null 반환
            }
        }
        return null;
    }

    // 카테고리에 맞는 음식점 리스트 반환
    public List<StoreDto> getStoresByCategoryId(Integer categoryId) {
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

    // 옵션 사용하여 음식점 리스트 반환
    public List<StoreDto> getStoresByOptions(String options) {
        List<Store> stores;

        if (options != null && options.equalsIgnoreCase("score>=4")) {
            // 옵션이 "score>=4"인 경우, score가 4 이상인 음식점 필터링
            stores = this.storeRepository.findByScoreGreaterThanEqual(4);
        } else {
            // 옵션이 없을 경우, 모든 음식점 반환
            stores = this.storeRepository.findAll();
        }

        return stores.stream()
                .map(store -> new StoreDto(
                        store.getStoreId(),
                        store.getStoreName(),
                        store.getRoadAddress(),
                        store.getLatitude(),
                        store.getLongitude()
                ))
                .collect(Collectors.toList());
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
                store.getScore());
    }
}
