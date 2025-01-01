package com.Food.Ordering.System.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartItemDTO {

    private Long food;
    private int quantity;
    private List<String> ingredients;
    private Long cartItemId;
}
