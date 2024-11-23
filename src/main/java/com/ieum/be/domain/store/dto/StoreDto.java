package com.ieum.be.domain.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDto {
    private Integer storeId;
    private String storeName;
    private String roadAddress;
    private Float latitude;
    private Float longitude;
    private String phone;
    private String imageUrl;
    private String kakaoMapUrl;

    public StoreDto(Integer storeId, String storeName, String roadAddress, Float latitude, Float longitude, String phone, String imageUrl, String kakaoMapUrl) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.roadAddress = roadAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.kakaoMapUrl = kakaoMapUrl;
    }
}

