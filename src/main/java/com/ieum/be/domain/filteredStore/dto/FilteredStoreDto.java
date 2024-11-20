package com.ieum.be.domain.filteredStore.dto;

public class FilteredStoreDto {
    private Integer fstoreId;
    private Integer storeId; // 참조하는 Store의 ID
    private String reason;

    public FilteredStoreDto(Integer fstoreId, Integer storeId, String reason) {
        this.fstoreId = fstoreId;
        this.storeId = storeId;
        this.reason = reason;
    }

    public FilteredStoreDto() {}
}
