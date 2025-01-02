package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.dto.FoodDTO;
import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.entity.Food;
import com.Food.Ordering.System.entity.IngredientsItem;
import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.exception.FoodNotFoundException;
import com.Food.Ordering.System.repository.FoodRepository;
import com.Food.Ordering.System.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private FoodRepository foodRepository;

    @Override
    public String addFood(FoodDTO foodDTO, Category category, Restaurant restaurant) {
        Food food = new Food();
        food.setName(foodDTO.getName());
        food.setDescription(foodDTO.getDescription());
        food.setPrice(foodDTO.getPrice());
        food.setCategory(category);
        food.setRestaurant(restaurant);
        food.setVegetarian(foodDTO.isVegetarian());
        food.setSeasonal(foodDTO.isSeasonal());
        food.setIngredientsItems(foodDTO.getIngredientsItems());
        food.setCreationDate(new Date());
        foodRepository.save(food);
        return "Food added successfully";


    }

    @Override
    public String updateFood(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodNotFoundException("Food not found with ID: " + foodId));
        food.setAvailable(true);
        foodRepository.save(food);
        return "Updated Successfully";
    }
//    private List<IngredientsItem> getIngredientsFromIds(List<Long> ingredientIds) {
//        // Fetch IngredientsItem entities by their IDs and return as a list
//        return ingredientsItemRepository.findAllById(ingredientIds);
//    }

    @Override
    public String deleteFood(Long foodId) {
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodNotFoundException("Food not found with ID: " + foodId));
        foodRepository.delete(food);
        return "Food Deleted successfully";
    }

    @Override
    public List<Food> getAllRestaurantFoods(Long restaurantId, boolean isVegetarian, boolean isNonVeg, boolean isSeasonal, String foodCategory) {
        List<Food> foods = foodRepository.findByRestaurantId(restaurantId);


        if (foods.isEmpty()) {
            throw new FoodNotFoundException("No foods found for restaurant ID: " + restaurantId);
        }
        if (isVegetarian) foods = foods.stream().filter(Food::isVegetarian).collect(Collectors.toList());
        if (isNonVeg) foods = foods.stream().filter(food -> !food.isVegetarian()).collect(Collectors.toList());
        if (isSeasonal) foods = foods.stream().filter(Food::isSeasonal).collect(Collectors.toList());
        if (foodCategory != null && !foodCategory.isEmpty()) {
            foods = foods.stream().filter(food -> food.getCategory().getName().equalsIgnoreCase(foodCategory)).collect(Collectors.toList());
        }
        return foods;
    }

    @Override
    public List<Food> searchFood(String keyword) {
        List<Food> foods = foodRepository.findByNameContainingIgnoreCase(keyword);
        if (foods.isEmpty()) {
            throw new FoodNotFoundException("No foods found with keyword: " + keyword);
        }
        return foods;
    }

    @Override
    public Food findFoodById(Long foodId) {
        return foodRepository.findById(foodId)
                .orElseThrow(() -> new FoodNotFoundException("Food not found with ID: " + foodId));
    }
}
