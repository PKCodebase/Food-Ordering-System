package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.exception.CategoryNotFoundException;
import com.Food.Ordering.System.repository.CategoryRepository;
import com.Food.Ordering.System.service.CategoryService;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public String addCategory(String name, Long restaurantId) {
        Category category = new Category();
        category.setName(name);
        category.setRestaurant(restaurantService.getRestaurantById(restaurantId));
        categoryRepository.save(category);
        return "Category added successfully";
    }

    @Override
    public String deleteCategory(Long categoryId) throws CategoryNotFoundException {
        Category category = findCategoryById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException("Category not found with ID: " + categoryId);
        }
        categoryRepository.delete(category);
        return "Category deleted successfully";
    }

    @Override
    public String updateCategory(Long categoryId, String name) throws CategoryNotFoundException {
        Category category = findCategoryById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException("Category not found with ID: " + categoryId);
        }
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
    public Category findCategoryById(Long categoryId) throws CategoryNotFoundException {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));
    }
}
