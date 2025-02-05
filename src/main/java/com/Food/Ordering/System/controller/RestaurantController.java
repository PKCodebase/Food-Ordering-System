package com.Food.Ordering.System.controller;


import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    // Add a new restaurant
    @PostMapping("/add")
    public ResponseEntity<String> addRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant response = restaurantService.addRestaurant(restaurant);
        return ResponseEntity.ok("Restaurant added successfully");
    }

    // Get restaurant by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(restaurant);
    }

    // Update restaurant by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRestaurantById(
            @PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
        String response = restaurantService.updateRestaurantById(id, updatedRestaurant);
        return ResponseEntity.ok(response);
    }

    // Delete restaurant by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable Long id) {
        String response = restaurantService.deleteRestaurantById(id);
        return ResponseEntity.ok(response);
    }

    // Get all restaurants
    @GetMapping("/all")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurant();
        return ResponseEntity.ok(restaurants);
    }

    // Search restaurants by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String keyword) {
        List<Restaurant> restaurants = restaurantService.searchRestaurant(keyword);
        return ResponseEntity.ok(restaurants);
    }
}
