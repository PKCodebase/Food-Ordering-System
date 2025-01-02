package com.Food.Ordering.System.dto;

import com.Food.Ordering.System.entity.Category;
import com.Food.Ordering.System.entity.IngredientsItem;
import lombok.Data;

import java.util.List;

@Data
public class FoodDTO {
    private String name;
    private String description;
    private Long price;
    private Long  categoryId;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredientsItems;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isSeasonal() {
        return seasonal;
    }

    public void setSeasonal(boolean seasonal) {
        this.seasonal = seasonal;
    }

    public List<IngredientsItem> getIngredientsItems() {
        return ingredientsItems;
    }

    public void setIngredientsItems(List<IngredientsItem> ingredientsItems) {
        this.ingredientsItems = ingredientsItems;
    }
}
