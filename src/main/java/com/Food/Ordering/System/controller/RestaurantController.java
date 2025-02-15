package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.exception.UserNotFoundException;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/add/{ownerId}")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<String> addRestaurant(@RequestBody Restaurant restaurant, @PathVariable Long ownerId) {
        return ResponseEntity.ok(restaurantService.addRestaurant(restaurant, ownerId));
    }


    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurantById(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_OWNER')")
    public ResponseEntity<String> updateRestaurantById(@PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
        return ResponseEntity.ok(restaurantService.updateRestaurantById(id, updatedRestaurant));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(restaurantService.deleteRestaurant(id));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllRestaurant());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String keyword) {
        return ResponseEntity.ok(restaurantService.searchRestaurant(keyword));
    }
}
