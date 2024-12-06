package com.ieum.be.domain.store.entity;

import com.ieum.be.domain.category.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer storeId;

    @Column(name = "store_name", nullable = false)
    private String storeName;

    @Column(name = "road_address", nullable = false)
    private String roadAddress;

    @Column(name = "phone")
    private String phone;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "score")
    private Float score;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "kakao_map_url")
    private String kakaoMapUrl;

    @Column(name = "average_price")
    private Long averagePrice;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Store(String storeName, String roadAddress, String phone, Float latitude, Float longitude, Float score, String imageUrl, Category category) {
        this.storeName = storeName;
        this.roadAddress = roadAddress;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Store() {}
}
