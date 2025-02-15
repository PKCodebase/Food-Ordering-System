package com.Food.Ordering.System.service;

import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.exception.UserNotFoundException;

import java.util.List;

public interface RestaurantService {

    String addRestaurant(Restaurant restaurant,Long ownerId);

    Restaurant getRestaurantById(Long restId);

    String updateRestaurantById(Long id, Restaurant restaurant);

    String deleteRestaurant(Long id) throws UserNotFoundException;

    List<Restaurant> getAllRestaurant();

    List<Restaurant> searchRestaurant(String keyword);

    List<Restaurant> findRestaurantByOwnerId(Long id);
}
