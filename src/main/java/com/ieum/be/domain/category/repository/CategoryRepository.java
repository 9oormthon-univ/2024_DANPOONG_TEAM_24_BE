package com.ieum.be.domain.category.repository;

import com.ieum.be.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findByCategoryId(Integer categoryId);
    Optional<Category> findByCategoryName(String categoryName);
}

