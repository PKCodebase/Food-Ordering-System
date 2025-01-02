package com.Food.Ordering.System.dto;

import lombok.Data;

@Data
public class IngredientCategoryDTO {

    private Long restaurantId;
    private String name;

    public IngredientCategoryDTO(Long restaurantId, String name) {
        this.restaurantId = restaurantId;
        this.name = name;
    }
    public IngredientCategoryDTO() {
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
