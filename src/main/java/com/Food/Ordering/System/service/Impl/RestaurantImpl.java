package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.exception.RestaurantNotFoundException;
import com.Food.Ordering.System.repository.RestaurantRepository;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RestaurantImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository  restaurantRepository;

    @Override
    public String addRestaurant(Restaurant restaurant) {
        if(restaurant.getName() == null || restaurant.getName().isEmpty()){
            throw new RestaurantNotFoundException("Restaurant name cannot be empty");
        }

        if(restaurant.getDescription() == null || restaurant.getDescription().isEmpty()){
            throw new RestaurantNotFoundException("Restaurant description cannot be empty");
        }
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setName(restaurant.getName());
        restaurant1.setDescription(restaurant.getDescription());
        restaurant1.setAddress(restaurant.getAddress());
        restaurant1.setCategories(restaurant.getCategories());

        Restaurant savedRestaurant = restaurantRepository.save(restaurant1);

        return "Restaurant added successfully with id: " + savedRestaurant.getId();
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return null;
    }

    @Override
    public String updateRestaurantById(Long id, Restaurant restaurant) {
        return "";
    }

    @Override
    public String deleteRestaurantById(Long id) {
        return "";
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return List.of();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return List.of();
    }
}
