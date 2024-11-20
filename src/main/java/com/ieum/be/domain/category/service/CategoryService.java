package com.ieum.be.domain.category.service;

import com.ieum.be.domain.category.dto.CategoryDto;
import com.ieum.be.domain.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDto> getAllCategories() {
        return this.categoryRepository.findAll().stream()
                .map(category -> new CategoryDto(
                        category.getCategoryId(),
                        category.getCategoryName()
                ))
                .collect(Collectors.toList());
    }
}

