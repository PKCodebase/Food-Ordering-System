package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.IngredientCategory;
import com.Food.Ordering.System.entity.IngredientsItem;
import com.Food.Ordering.System.exception.CategoryNotFoundException;
import com.Food.Ordering.System.exception.IngredientCategoryNotFound;
import com.Food.Ordering.System.repository.IngredientCategoryRepository;
import com.Food.Ordering.System.repository.IngredientsItemRepository;
import com.Food.Ordering.System.service.IngredientsService;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientsServiceImpl implements IngredientsService {

    @Autowired
    private IngredientCategoryRepository ingredientCategoryRepository;

    @Autowired
    private IngredientsItemRepository ingredientsItemRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public String addIngredients(String name, Long restaurantId) {
        IngredientCategory ingredientCategory = new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurantService.getRestaurantById(restaurantId));
        ingredientCategoryRepository.save(ingredientCategory);
        return "Ingredient category added successfully";
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) {
        return ingredientCategoryRepository.findById(id)
                .orElseThrow(() -> new IngredientCategoryNotFound("Ingredient category not found"));
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long restaurantId) throws CategoryNotFoundException {
        List<IngredientCategory> ingredientCategories = ingredientCategoryRepository.findByRestaurantId(restaurantId);
        if (ingredientCategories.isEmpty()) {
            throw new CategoryNotFoundException("No categories found for restaurant ID: " + restaurantId);
        }
        return ingredientCategories;

    }

    @Override
    public String createIngredientsItem(Long restaurantId, String ingredientName, Long ingredientCategoryId) {
        IngredientCategory ingredientCategory = findIngredientCategoryById(ingredientCategoryId);
        IngredientsItem ingredientsItem = new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurantService.getRestaurantById(restaurantId));
        ingredientsItem.setIngredientCategory(ingredientCategory);
        ingredientsItem.setAvailable(false);
        ingredientsItemRepository.save(ingredientsItem);
        return "Ingredient item added successfully";
    }

    @Override
    public IngredientsItem updateStock(Long id) {

        IngredientsItem ingredientsItem = ingredientsItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingredient Item Not Found"));

        // Toggle the inStock status
        ingredientsItem.setAvailable(!ingredientsItem.isAvailable());

        // Save and return the updated item
        return ingredientsItemRepository.save(ingredientsItem);
    }

}
