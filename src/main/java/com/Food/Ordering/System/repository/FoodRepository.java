package com.Food.Ordering.System.repository;


import com.Food.Ordering.System.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository  extends  JpaRepository<Food, Long>{
    List<Food> findByRestaurantId(Long restaurantId);

    List<Food> findByNameContainingIgnoreCase(String keyword);
}
