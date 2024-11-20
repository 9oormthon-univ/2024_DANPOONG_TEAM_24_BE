package com.ieum.be.repository;

import com.ieum.be.domain.FilteredStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilteredStoreRepository extends JpaRepository<FilteredStore, Integer> {
}

