package com.Food.Ordering.System.service;

import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.entity.Restaurant;

import java.util.List;

public interface RestaurantService {

    String addRestaurant(Restaurant restaurant);

    Restaurant getRestaurantById(Long restid);

    String updateRestaurantById(Long id, Restaurant restaurant);

    String deleteRestaurantById(Long id);

    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyword);

}
