package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.entity.Food;
import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.exception.CategoryNotFoundException;
import com.Food.Ordering.System.exception.FoodNotFoundException;
import com.Food.Ordering.System.service.CategoryService;
import com.Food.Ordering.System.service.FoodService;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RestaurantService restaurantService;

    // ✅ Admin & Owner can add food
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OWNER')")
    @PostMapping("/add")
    public ResponseEntity<String> addFood(@RequestBody Food food,
                                          @RequestParam Long categoryId,
                                          @RequestParam Long restaurantId) throws CategoryNotFoundException {
        Category category = categoryService.findCategoryById(categoryId);
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        if (category == null) {
            throw new CategoryNotFoundException("Category not found with ID: " + categoryId);
        }
        if (restaurant == null) {
            throw new RuntimeException("Restaurant not found with ID: " + restaurantId);
        }

        String response = foodService.addFood(food, category, restaurant);
        return ResponseEntity.ok(response);
    }



    // ✅ Admin & User can get all foods by restaurant
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Food>> getAllRestaurantFoods(
            @PathVariable Long restaurantId,
            @RequestParam(defaultValue = "false") boolean isVegetarian,
            @RequestParam(defaultValue = "false") boolean isNonVeg,
            @RequestParam(defaultValue = "false") boolean isSeasonal,
            @RequestParam(required = false) String foodCategory) {
        List<Food> foods = foodService.getAllRestaurantFoods(restaurantId, isVegetarian, isNonVeg, isSeasonal, foodCategory);
        return ResponseEntity.ok(foods);
    }

    // ✅ Search Food (No restrictions, available for all authenticated users)
    @GetMapping("/search")
    public ResponseEntity<List<Food>> searchFood(@RequestParam String keyword) {
        List<Food> foods = foodService.searchFood(keyword);
        return ResponseEntity.ok(foods);
    }

    // ✅ Get Food by ID (No restrictions, available for all authenticated users)
    @GetMapping("/{foodId}")
    public ResponseEntity<Food> getFoodById(@PathVariable Long foodId) {
        Food food = foodService.findFoodById(foodId);
        return ResponseEntity.ok(food);
    }

    // ✅ Admin can delete food
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OWNER')")
    @PutMapping("/update/{foodId}")
    public ResponseEntity<String> updateFood(@PathVariable Long foodId, @RequestBody Food updatedFood) {
        String response = foodService.updateFood(foodId, updatedFood);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OWNER')")
    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<String> deleteFood(@PathVariable Long foodId) {
        String response = foodService.deleteFood(foodId);
        return ResponseEntity.ok(response);
    }
}
