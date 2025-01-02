package com.Food.Ordering.System.exception;

public class FoodNotFoundException extends RuntimeException{
    public FoodNotFoundException(String message) {
        super(message);
    }
}
