package com.Food.Ordering.System.repository;


import com.Food.Ordering.System.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository  extends  JpaRepository<Food, Long>{
}
