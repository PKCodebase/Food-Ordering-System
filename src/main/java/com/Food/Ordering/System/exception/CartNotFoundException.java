package com.Food.Ordering.System.exception;

public class CartNotFoundException extends Throwable {
    public CartNotFoundException(String message) {
        super(message);
    }
}
