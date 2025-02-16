package com.Food.Ordering.System.service;

import com.Food.Ordering.System.entity.Cart;
import com.Food.Ordering.System.entity.CartItem;
import com.Food.Ordering.System.entity.Food;
import com.Food.Ordering.System.exception.CartItemNotFoundException;
import com.Food.Ordering.System.exception.CartNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;

public interface CartService {

    CartItem addProductToCart(Long userId, Food food, Integer quantity) throws CartNotFoundException, UserNotFoundException;

    CartItem updateCartItem(Long cartItemId, Integer quantity) throws CartItemNotFoundException;

    Cart removeProductFromCart(Long userId, Long foodId) throws CartNotFoundException, UserNotFoundException;

    Double calculateCartTotal(Long userId) throws CartNotFoundException;

    Cart getCart(Long userId) throws UserNotFoundException, CartNotFoundException;

    Cart clearCart(Long userId) throws CartNotFoundException;
}
