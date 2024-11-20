package com.ieum.be.domain.store.repository;

import com.ieum.be.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    // categoryId로 식당 리스트 찾기
    List<Store> findByCategory_CategoryId(Integer categoryId);

    // storeId로 찾기
    Optional<Store> findByStoreId(Integer storeId);

    // Find stores with filters
}
