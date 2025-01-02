package com.Food.Ordering.System.service;

import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.exception.CategoryNotFoundException;

import java.util.List;

public interface CategoryService {

    String addCategory(String name,Long restaurantId);

    String  deleteCategory(Long categoryId) throws CategoryNotFoundException;

    String updateCategory(Long categoryId, String name) throws CategoryNotFoundException;

    List<Category> findCategoryByRestaurantId(Long restaurantId) throws CategoryNotFoundException;

    Category findCategoryById(Long categoryId) throws CategoryNotFoundException;
}
