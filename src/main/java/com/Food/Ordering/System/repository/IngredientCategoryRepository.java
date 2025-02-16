package com.Food.Ordering.System.repository;

import com.Food.Ordering.System.entity.IngredientCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientCategoryRepository extends JpaRepository<IngredientCategory, Long> {
    List<IngredientCategory> findByRestaurantId(Long id);
}
