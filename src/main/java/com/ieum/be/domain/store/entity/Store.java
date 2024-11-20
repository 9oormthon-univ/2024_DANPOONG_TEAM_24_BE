package com.ieum.be.domain.store.entity;

import com.ieum.be.domain.category.entity.Category;
import com.ieum.be.domain.filteredStore.entity.FilteredStore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "lot_address")
    private String lotAddress;

    @Column(name = "phone")
    private String phone;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilteredStore> filteredStores = new ArrayList<>();

    public Store(Integer storeId, String storeName, String roadAddress, String lotAddress, String phone, Float latitude, Float longitude, String imageUrl, Category category) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.roadAddress = roadAddress;
        this.lotAddress = lotAddress;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public Store() {}
}
