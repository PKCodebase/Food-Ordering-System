package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.exception.CategoryNotFoundException;
import com.Food.Ordering.System.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Add Category (Only Admin & Owner)
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OWNER')")
    public ResponseEntity<String> addCategory(@RequestParam String name, @RequestParam Long restaurantId) {
        String response = categoryService.addCategory(name, restaurantId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Delete a Category (Only Admin & Owner)
    @DeleteMapping("/delete/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OWNER')")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId,
                                                 @RequestParam Long restaurantId) {
        try {
            String response = categoryService.deleteCategory(categoryId, restaurantId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    //  Update a Category (Only Admin & Owner)
    @PutMapping("/update/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_OWNER')")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId, @RequestParam String name) {
        try {
            String response = categoryService.updateCategory(categoryId, name);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    //  Find Categories by Restaurant ID (Admin & Owner)
    @GetMapping("/restaurant/{restaurantId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> findCategoriesByRestaurantId(@PathVariable Long restaurantId) {
        try {
            List<Category> categories = categoryService.findCategoryByRestaurantId(restaurantId);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Find Category by ID (All Users)
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> findCategoryById(@PathVariable Long categoryId) {
        try {
            Category category = categoryService.findCategoryById(categoryId);
            return ResponseEntity.ok(category);
        } catch (CategoryNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/searchByCategory")
    public ResponseEntity<?> findRestaurantsByCategoryName(@RequestParam String name) {
        try {
            List<Restaurant> restaurants = categoryService.findRestaurantsByCategoryName(name);
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
