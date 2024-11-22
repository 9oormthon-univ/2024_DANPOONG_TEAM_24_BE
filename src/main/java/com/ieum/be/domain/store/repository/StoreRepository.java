package com.ieum.be.domain.store.repository;

import com.ieum.be.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    // 모든 식당 리스트 찾기
    List<Store> findAll();

    // storeId로 식당 찾기
    Optional<Store> findByStoreId(Integer storeId);


    @Query(value = """
        SELECT * 
        FROM store 
        WHERE category_id = :categoryId 
          AND ST_Distance_Sphere(location, ST_SRID(POINT(:longitude, :latitude), 4326)) <= :distance
    """, nativeQuery = true)
    List<Store> findStoresByCategoryAndDistance(
            @Param("categoryId") Integer categoryId,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("distance") Integer distance
    );

    @Query(value = """
        SELECT * 
        FROM store 
        WHERE score >= :score 
          AND ST_Distance_Sphere(location, ST_SRID(POINT(:longitude, :latitude), 4326)) <= :distance
    """, nativeQuery = true)
    List<Store> findByScoreAndDistance(
            @Param("score") float score,
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("distance") Integer distance
    );

    @Query(value = """
        SELECT * 
        FROM store 
        WHERE ST_Distance_Sphere(location, ST_SRID(POINT(:longitude, :latitude), 4326)) <= :distance
    """, nativeQuery = true)
    List<Store> findStoresByDistance(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("distance") Integer distance
    );
}
