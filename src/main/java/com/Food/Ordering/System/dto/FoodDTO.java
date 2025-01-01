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
    private Category  category;
    private List<String> images;
    private Long restaurantId;
    private boolean vegetarian;
    private boolean seasonal;
    private List<IngredientsItem> ingredientsItems;
}
