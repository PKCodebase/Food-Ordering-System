package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.entity.IngredientCategory;
import com.Food.Ordering.System.entity.IngredientsItem;
import com.Food.Ordering.System.exception.CategoryNotFoundException;
import com.Food.Ordering.System.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/category/add")
    public ResponseEntity<String> addIngredientCategory(@RequestParam String name, @RequestParam Long restaurantId) {
        try {
            String response = ingredientsService.addIngredients(name, restaurantId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to get all ingredient categories for a restaurant
    @GetMapping("/category/restaurant/{restaurantId}")
    public ResponseEntity<List<IngredientCategory>> getIngredientsByRestaurant(@PathVariable Long restaurantId) throws CategoryNotFoundException {
        List<IngredientCategory> ingredientCategories = ingredientsService.findIngredientCategoryByRestaurantId(restaurantId);
        return new ResponseEntity<>(ingredientCategories, HttpStatus.OK);
    }

    // Endpoint to get a specific ingredient category by ID
    @GetMapping("/get/category/{id}")
    public ResponseEntity<IngredientCategory> getIngredientCategoryById(@PathVariable Long id) {
        IngredientCategory ingredientCategory = ingredientsService.findIngredientCategoryById(id);
        if (ingredientCategory != null) {
            return new ResponseEntity<>(ingredientCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to create a new ingredient item
    @PostMapping("/item/add")
    public ResponseEntity<String> createIngredientItem(@RequestParam Long restaurantId,
                                                       @RequestParam String ingredientName,
                                                       @RequestParam Long ingredientCategoryId) {
        try {
            String response = ingredientsService.createIngredientsItem(restaurantId, ingredientName, ingredientCategoryId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to update stock of a specific ingredient item
    @PutMapping("/update/{id}")
    public ResponseEntity<IngredientsItem> updateIngredientStock(@PathVariable Long id) {
        IngredientsItem updatedItem = ingredientsService.updateStock(id);
        if (updatedItem != null) {
            return new ResponseEntity<>(updatedItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
