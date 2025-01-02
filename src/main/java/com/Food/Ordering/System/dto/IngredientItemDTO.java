package com.Food.Ordering.System.dto;

import lombok.Data;

@Data
public class IngredientItemDTO {

    private String name;
    private Long restaurantId;
    private Long categoryId;

    public IngredientItemDTO(String name, Long restaurantId, Long categoryId) {
        this.name = name;
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
    }
    public IngredientItemDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
