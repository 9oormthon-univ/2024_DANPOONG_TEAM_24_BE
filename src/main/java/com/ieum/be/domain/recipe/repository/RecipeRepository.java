package com.ieum.be.domain.recipe.repository;

import com.ieum.be.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findAll();

    Optional<Recipe> findById(Integer recipeId);

    List<Recipe> findTop10ByOrderByRecipeIdDesc();  // 추천 레시피 리스트 호출 예시
}
