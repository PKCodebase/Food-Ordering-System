package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.exception.RestaurantNotFoundException;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // Add a new restaurant
    @PostMapping("/add")
    public ResponseEntity<String> addRestaurant(@RequestBody Restaurant restaurant) {
        try {
            String response = restaurantService.addRestaurant(restaurant);
            return ResponseEntity.ok(response);
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get restaurant by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable Long id) {
        try {
            Restaurant restaurant = restaurantService.getRestaurantById(id);
            return ResponseEntity.ok(restaurant);
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Update restaurant by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRestaurantById(@PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
        try {
            String response = restaurantService.updateRestaurantById(id, updatedRestaurant);
            return ResponseEntity.ok(response);
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Delete restaurant by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable Long id) {
        try {
            String response = restaurantService.deleteRestaurantById(id);
            return ResponseEntity.ok(response);
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get all restaurants
    @GetMapping("/all")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        return ResponseEntity.ok(restaurants);
    }

    // Search restaurants by keyword
    @GetMapping("/search")
    public ResponseEntity<?> searchRestaurants(@RequestParam String keyword) {
        try {
            List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
            return ResponseEntity.ok(restaurants);
        } catch (RestaurantNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
