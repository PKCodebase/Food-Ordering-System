package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.exception.CategoryNotFoundException;
import com.Food.Ordering.System.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@RequestParam String name, @RequestParam Long restaurantId) {
        try {
            String response = categoryService.addCategory(name, restaurantId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a category by ID
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            String response = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Update a category
    @PutMapping("/update/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestParam String name) {
        try {
            String response = categoryService.updateCategory(categoryId, name);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Find categories by restaurant ID
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> findCategoriesByRestaurantId(@PathVariable Long restaurantId) {
        try {
            List<Category> categories = categoryService.findCategoryByRestaurantId(restaurantId);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Find category by ID
    @GetMapping("/{categoryId}")
    public ResponseEntity<String> findCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.findCategoryById(categoryId);
            return ResponseEntity.ok("Category found: " + category.getName());
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(404).body("Category not available with ID: " + categoryId);
        }

    }
}

