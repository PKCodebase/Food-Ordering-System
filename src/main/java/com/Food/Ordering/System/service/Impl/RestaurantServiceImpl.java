package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.Address;
import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.exception.RestaurantNotFoundException;
import com.Food.Ordering.System.repository.AddressRepository;
import com.Food.Ordering.System.repository.RestaurantRepository;
import com.Food.Ordering.System.repository.UserRepository;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String addRestaurant(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant name cannot be empty");
        }

        if (restaurant.getDescription() == null || restaurant.getDescription().isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant description cannot be empty");
        }

        // Validate owner
        if (restaurant.getOwner() == null || userRepository.findById(restaurant.getOwner().getId()).isEmpty()) {
            throw new RestaurantNotFoundException("Valid owner is required for the restaurant");
        }

        // Validate and persist address
        if (restaurant.getAddress() == null) {
            throw new RestaurantNotFoundException("Address cannot be null");
        }

        Address address = restaurant.getAddress();
        if (address.getId() == null) { // Save only if it's a new address
            address = addressRepository.save(address);
        }
        restaurant.setAddress(address);
        address.setRestaurant(restaurant); // Maintain bidirectional relationship

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return "Restaurant added successfully with ID: " + savedRestaurant.getId();
    }

    @Override
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + id));
    }

    @Transactional
    @Override
    public String updateRestaurantById(Long id, Restaurant updatedRestaurant) {
        Restaurant restaurant = getRestaurantById(id);

        restaurant.setName(updatedRestaurant.getName());
        restaurant.setDescription(updatedRestaurant.getDescription());
        restaurant.setOpeningHours(updatedRestaurant.getOpeningHours());

        // Handle Address updates safely
        if (updatedRestaurant.getAddress() != null) {
            Address newAddress = updatedRestaurant.getAddress();
            if (newAddress.getId() == null) { // Save only if new
                newAddress = addressRepository.save(newAddress);
            }
            restaurant.setAddress(newAddress);
            newAddress.setRestaurant(restaurant); // Maintain bidirectional mapping
        }

        restaurant.setCategories(updatedRestaurant.getCategories());
        restaurant.setContactInformation(updatedRestaurant.getContactInformation());

        restaurantRepository.save(restaurant);
        return "Restaurant updated successfully with ID: " + id;
    }

    @Transactional
    @Override
    public String deleteRestaurantById(Long id) {
        Restaurant restaurant = getRestaurantById(id);
        restaurantRepository.delete(restaurant);
        return "Restaurant deleted successfully with ID: " + id;
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        List<Restaurant> restaurants = restaurantRepository.findByNameContainingIgnoreCase(keyword);

        if (restaurants.isEmpty()) {
            throw new RestaurantNotFoundException("No restaurants found: " + keyword);
        }

        return restaurants;
    }
}
