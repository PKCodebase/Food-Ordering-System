package com.Food.Ordering.System.service;

import com.Food.Ordering.System.entity.IngredientCategory;
import com.Food.Ordering.System.entity.IngredientsItem;
import com.Food.Ordering.System.exception.CategoryNotFoundException;

import java.util.List;

public interface IngredientsService {

    String addIngredients(String name, Long restaurantId);

    IngredientCategory findIngredientCategoryById(Long id);

    List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws CategoryNotFoundException;


    String createIngredientsItem(Long restaurantId, String ingredientName, Long ingredientCategoryId) throws Exception;

    IngredientsItem updateStock(Long id);
}
