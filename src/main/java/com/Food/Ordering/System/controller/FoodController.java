package com.Food.Ordering.System.controller;


import com.Food.Ordering.System.dto.FoodDTO;
import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.entity.Food;
import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.exception.CategoryNotFoundException;
import com.Food.Ordering.System.exception.FoodNotFoundException;
import com.Food.Ordering.System.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private CategoryService categoryService;


    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<String> addFood(@RequestBody FoodDTO foodDTO,
                                          @RequestParam Long categoryId,
                                          @RequestParam Long restaurantId) {
        try {
            Category category = categoryService.findCategoryById(categoryId);
            Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
            String response = foodService.addFood(foodDTO, category, restaurant);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding food: " + e.getMessage());
        } catch (CategoryNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // Endpoint to update food availability
    @PutMapping("/update/{foodId}")
    public ResponseEntity<String> updateFood(@PathVariable Long foodId) {
        try {
            String response = foodService.updateFood(foodId);
            return ResponseEntity.ok(response);
        } catch (FoodNotFoundException e) {
            return ResponseEntity.status(404).body("Food not found with ID: " + foodId);
        }
    }

    // Endpoint to delete food
    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<String> deleteFood(@PathVariable Long foodId) {
        try {
            String response = foodService.deleteFood(foodId);
            return ResponseEntity.ok(response);
        } catch (FoodNotFoundException e) {
            return ResponseEntity.status(404).body("Food not found with ID: " + foodId);
        }
    }

    // Endpoint to get all foods for a specific restaurant with filters
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getAllRestaurantFoods(@PathVariable Long restaurantId,
                                                            @RequestParam(defaultValue = "false") boolean isVegetarian,
                                                            @RequestParam(defaultValue = "false") boolean isNonVeg,
                                                            @RequestParam(defaultValue = "false") boolean isSeasonal,
                                                            @RequestParam(required = false) String foodCategory) {
        try {
            List<Food> foods = foodService.getAllRestaurantFoods(restaurantId, isVegetarian, isNonVeg, isSeasonal, foodCategory);
            return ResponseEntity.ok(foods);
        } catch (FoodNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Endpoint to search food by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword) {
        try {
            List<Food> foods = foodService.searchFood(keyword);
            return ResponseEntity.ok(foods);
        } catch (FoodNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    // Endpoint to get food details by ID
    @GetMapping("/{foodId}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long foodId) {
        try {
            Food food = foodService.findFoodById(foodId);
            return ResponseEntity.ok(food);
        } catch (FoodNotFoundException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
}



