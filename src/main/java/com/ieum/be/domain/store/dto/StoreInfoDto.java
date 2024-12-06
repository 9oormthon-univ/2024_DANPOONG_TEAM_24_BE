package com.ieum.be.domain.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreInfoDto {
    private Integer storeId;
    private String storeName;
    private String roadAddress;
    private String phone;
    private String imageUrl;
    private Float score;
    private String averagePrice;

    public StoreInfoDto(Integer storeId, String storeName, String roadAddress, String phone, String imageUrl, Float score, Long averagePrice) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.roadAddress = roadAddress;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.score = score;
        this.averagePrice = averagePrice == null ? "가격 정보 미제공" : averagePrice + "원";
    }
}

