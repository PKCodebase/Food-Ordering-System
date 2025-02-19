package com.Food.Ordering.System.service;


import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.entity.Food;
import com.Food.Ordering.System.entity.Restaurant;

import java.util.List;

public interface FoodService {

    String addFood(Food foodD, Category category, Restaurant restaurant);

    String updateFood(Long foodId, Food updatedFood);

    String deleteFood(Long foodId);

    List<Food> getAllRestaurantFoods(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory);

    List<Food> searchFood(String keyword);

    Food findFoodById(Long foodId);



}
