package com.ieum.be.domain.post.repository;

import com.ieum.be.domain.post.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
    Optional<PostCategory> findPostCategoryByCategoryName(String categoryName);
}
