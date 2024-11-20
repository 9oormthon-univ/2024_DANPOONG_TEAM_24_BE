package com.ieum.be.domain.store.dto;

public class StoreDto {
    private Integer storeId;
    private String storeName;
    private String roadAddress;
    private Float latitude;
    private Float longitude;

    public StoreDto(Integer storeId, String storeName, String roadAddress, Float latitude, Float longitude) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public StoreDto() {}
}

