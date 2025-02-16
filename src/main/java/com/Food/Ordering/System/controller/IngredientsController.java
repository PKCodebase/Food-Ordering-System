package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.entity.IngredientCategory;
import com.Food.Ordering.System.entity.IngredientsItem;
import com.Food.Ordering.System.exception.CategoryNotFoundException;
import com.Food.Ordering.System.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientsService ingredientsService;

    // ✅ Add Ingredient Category
    @PostMapping("/category/add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OWNER')")
    public ResponseEntity<String> addIngredientCategory(@RequestParam String name, @RequestParam Long restaurantId) {
        try {
            String response = ingredientsService.addIngredients(name, restaurantId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Get all ingredient categories for a restaurant
    @GetMapping("/category/restaurant/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OWNER')")
    public ResponseEntity<List<IngredientCategory>> getIngredientsByRestaurant(@PathVariable Long restaurantId) {
        List<IngredientCategory> ingredientCategories = ingredientsService.findIngredientCategoryByRestaurantId(restaurantId);
        return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
    }

    // ✅ Get a specific ingredient category by ID
    @GetMapping("/get/category/{id}")
    public ResponseEntity<IngredientCategory> getIngredientCategoryById(@PathVariable Long id) {
        IngredientCategory ingredientCategory = ingredientsService.findIngredientCategoryById(id);
        return new ResponseEntity<>(ingredientCategory, HttpStatus.OK);
    }

    // ✅ Create a new ingredient item
    @PostMapping("/item/add")
    public ResponseEntity<String> createIngredientItem(
            @RequestParam Long restaurantId,
            @RequestParam String ingredientName,
            @RequestParam Long ingredientCategoryId,
            @RequestParam Long price) {  // Now taking price as input
        try {
            String response = ingredientsService.createIngredientsItem(restaurantId, ingredientName, ingredientCategoryId, price);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ Update Ingredient Stock and Price
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OWNER')")
    public ResponseEntity<IngredientsItem> updateIngredientStock(
            @PathVariable Long id,
            @RequestParam(required = false) Long price) {
        IngredientsItem updatedItem = ingredientsService.updateStock(id, price);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }
}
