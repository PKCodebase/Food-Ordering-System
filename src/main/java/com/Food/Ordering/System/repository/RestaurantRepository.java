package com.Food.Ordering.System.repository;

import com.Food.Ordering.System.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends  JpaRepository<Restaurant, Long>{
    List<Restaurant> findByNameContainingIgnoreCase(String keyword);
}
