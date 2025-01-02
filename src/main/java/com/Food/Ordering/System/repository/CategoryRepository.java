package com.Food.Ordering.System.repository;

import com.Food.Ordering.System.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    List<Category> findByRestaurantId(Long restaurantId);
}
