package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.Address;
import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.OwnerNotFoundException;
import com.Food.Ordering.System.exception.RestaurantNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;
import com.Food.Ordering.System.repository.AddressRepository;
import com.Food.Ordering.System.repository.RestaurantRepository;
import com.Food.Ordering.System.repository.UserRepository;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository; // Add this repository

    private static final Logger logger = Logger.getLogger(RestaurantServiceImpl.class.getName());

    @Override
    public String addRestaurant(Restaurant restaurant, Long ownerId) {
        if (restaurant.getName() == null || restaurant.getDescription() == null) {
            throw new RestaurantNotFoundException("Restaurant name and description are required");
        }

        logger.info("Fetching owner with ID: " + ownerId);

        // Fetch owner (user) from database
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found with id: " + ownerId));

        restaurant.setOwner(owner); // Set restaurant owner

        Address address = restaurant.getAddress();
        if (address != null) {
            address.setUser(owner); // Link address to owner
            address = addressRepository.save(address);
            restaurant.setAddress(address);
        }

        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurantRepository.save(restaurant);

        logger.info("Restaurant added successfully with ID: " + restaurant.getId());
        return "Restaurant added successfully";
    }

    @Override
    public Restaurant getRestaurantById(Long restId) {
        return restaurantRepository.findById(restId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));
    }

    @Override
    public String updateRestaurantById(Long id, Restaurant restaurant) {
        Restaurant existingRestaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + id));

        if (restaurant.getName() != null) {
            existingRestaurant.setName(restaurant.getName());
        }
        if (restaurant.getDescription() != null) {
            existingRestaurant.setDescription(restaurant.getDescription());
        }
        if (restaurant.getFoodType() != null) {
            existingRestaurant.setFoodType(restaurant.getFoodType());
        }
        if (restaurant.getOpeningHours() != null) {
            existingRestaurant.setOpeningHours(restaurant.getOpeningHours());
        }
        if (restaurant.getContactInformation() != null) {
            existingRestaurant.setContactInformation(restaurant.getContactInformation());
        }

        // ✅ Correct way to update Address without replacing it
        if (restaurant.getAddress() != null) {
            Address existingAddress = existingRestaurant.getAddress();
            Address newAddress = restaurant.getAddress();

            if (existingAddress != null) {
                existingAddress.setStreet(newAddress.getStreet());
                existingAddress.setCity(newAddress.getCity());
                existingAddress.setState(newAddress.getState());
                existingAddress.setPostalCode(newAddress.getPostalCode());
                existingAddress.setCountry(newAddress.getCountry());

                addressRepository.save(existingAddress); // Save updated address
            } else {
                newAddress.setUser(existingRestaurant.getOwner());
                existingRestaurant.setAddress(addressRepository.save(newAddress));
            }
        }

        existingRestaurant.setOpen(restaurant.isOpen());
        restaurantRepository.save(existingRestaurant);

        return "Restaurant updated successfully";
    }





    @Override
    public String deleteRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found"));

        Address address = restaurant.getAddress();

        // ✅ Delete the address first to avoid foreign key constraint issues
        if (address != null) {
            addressRepository.delete(address);
        }

        // ✅ Now delete the restaurant
        restaurantRepository.delete(restaurant);

        return "Restaurant and associated address deleted successfully";
    }


    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        List<Restaurant> restaurants = restaurantRepository.findByNameContainingIgnoreCase(keyword);
        if (restaurants.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant not found");
        }
        return restaurants;
    }

    @Override
    public List<Restaurant> findRestaurantByOwnerId(Long id) {
        List<Restaurant> restaurants = restaurantRepository.findByOwnerId(id);
        if (restaurants.isEmpty()) {
            throw new RestaurantNotFoundException("No restaurants found for this owner");
        }
        return restaurants;
    }
}
