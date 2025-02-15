package com.Food.Ordering.System.repository;

import com.Food.Ordering.System.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
            "OR LOWER(r.foodType) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Restaurant> findByNameContainingIgnoreCase(String query);

    List<Restaurant> findByOwnerId(Long userId);
}
