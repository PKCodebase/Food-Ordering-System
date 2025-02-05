package com.Food.Ordering.System.service;


import com.Food.Ordering.System.entity.Cart;
import com.Food.Ordering.System.entity.CartItem;
import com.Food.Ordering.System.exception.CartNotFoundException;

public interface CartService {

    String  addProductToCart(CartItem cartItem, String email) throws CartNotFoundException;

    String updateCart(Long cartItemId, int quantity) throws CartNotFoundException;

    Cart removeProductToCart(Long productId, String jwt) throws CartNotFoundException;

    Long calculateCartTotals(Cart id) throws CartNotFoundException;

    public Cart findCartById(Long id) throws Exception;

    public Cart findCartByUserId(Long userId) throws Exception;

    String clearCart(Long userId);

}
