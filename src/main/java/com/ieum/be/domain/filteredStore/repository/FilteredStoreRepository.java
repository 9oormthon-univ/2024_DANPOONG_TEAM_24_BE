package com.ieum.be.domain.filteredStore.repository;

import com.ieum.be.domain.filteredStore.entity.FilteredStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilteredStoreRepository extends JpaRepository<FilteredStore, Integer> {
}

