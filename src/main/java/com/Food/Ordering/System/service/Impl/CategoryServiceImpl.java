package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.entity.Restaurant;
import com.Food.Ordering.System.exception.CategoryNotFoundException;
import com.Food.Ordering.System.exception.RestaurantNotFoundException;
import com.Food.Ordering.System.repository.CategoryRepository;
import com.Food.Ordering.System.service.CategoryService;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public String addCategory(String name, Long restaurantId) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNotFoundException("Restaurant not found with ID: " + restaurantId);
        }

        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurant);

        categoryRepository.save(category);
        return "Category added successfully";
    }

    @Override
    public String deleteCategory(Long categoryId, Long restaurantId) throws CategoryNotFoundException {
        Category category = findCategoryById(categoryId);

        if (!category.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Category does not belong to the specified restaurant.");
        }

        // Remove category from the restaurant before deleting it
        Restaurant restaurant = category.getRestaurant();
        restaurant.getCategories().remove(category);

        categoryRepository.delete(category);
        return "Category deleted successfully";
    }


    @Override
    public String updateCategory(Long categoryId, String name) throws CategoryNotFoundException {
        Category category = findCategoryById(categoryId);
        category.setName(name);
        categoryRepository.save(category);
        return "Category updated successfully";
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long restaurantId) throws CategoryNotFoundException {
        List<Category> categories = categoryRepository.findByRestaurantId(restaurantId);
        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("No categories found for restaurant ID: " + restaurantId);
        }
        return categories;
    }

    @Override
    public List<Restaurant> findRestaurantsByCategoryName(String name) throws CategoryNotFoundException {
        List<Category> categories = categoryRepository.findByName(name);

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("Category not found with name: " + name);
        }

        // ✅ Extract restaurants from categories
        List<Restaurant> restaurants = categories.stream()
                .map(Category::getRestaurant)
                .distinct() // Remove duplicate restaurants
                .collect(Collectors.toList());

        return restaurants;
    }

    @Override
    public Category findCategoryById(Long categoryId) throws CategoryNotFoundException {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
    }
}
