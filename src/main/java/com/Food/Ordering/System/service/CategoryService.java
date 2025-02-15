package com.Food.Ordering.System.service;

import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.exception.CategoryNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    String addCategory(String name, Long restaurantId);

    String deleteCategory(Long categoryId,Long restaurantId) throws CategoryNotFoundException;

    String updateCategory(Long categoryId, String name) throws CategoryNotFoundException;

    List<Category> findCategoryByRestaurantId(Long restaurantId) throws CategoryNotFoundException;

    List<Restaurant> findRestaurantsByCategoryName(String name) throws CategoryNotFoundException;

    Category findCategoryById(Long categoryId) throws CategoryNotFoundException;
}
