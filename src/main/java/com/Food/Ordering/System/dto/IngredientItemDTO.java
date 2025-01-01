package com.Food.Ordering.System.dto;

import lombok.Data;

@Data
public class IngredientItemDTO {

    private String name;
    private Long restaurantId;
    private Long categoryId;
}
