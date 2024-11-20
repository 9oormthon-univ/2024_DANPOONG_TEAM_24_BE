package com.ieum.be.domain.store.dto;

public class StoreInfoDto {
    private Integer storeId;
    private String storeName;
    private String roadAddress;
    private String phone;
    private String imageUrl;

    public StoreInfoDto(Integer storeId, String storeName, String roadAddress, String phone, String imageUrl) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.roadAddress = roadAddress;
        this.phone = phone;
        this.imageUrl = imageUrl;
    }
}

