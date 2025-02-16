package com.Food.Ordering.System.repository;

import com.Food.Ordering.System.entity.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientsItemRepository extends JpaRepository<IngredientsItem, Long> {
    List<IngredientsItem> findByRestaurantId(Long restaurantId);
}
