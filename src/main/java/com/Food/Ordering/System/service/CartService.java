package com.Food.Ordering.System.service;


import com.Food.Ordering.System.entity.Cart;
import com.Food.Ordering.System.entity.CartItem;
import com.Food.Ordering.System.exception.CartNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;

public interface CartService {

    CartItem  addProductToCart(CartItem cartItem, String email) throws CartNotFoundException, UserNotFoundException;

    CartItem updateCart(Long cartItemId, int quantity) throws CartNotFoundException;

    Cart removeProductToCart(Long productId, String jwt) throws CartNotFoundException, UserNotFoundException;

    Long calculateCartTotals(Cart id) throws CartNotFoundException;

    public Cart findCartById(Long id) throws Exception, CartNotFoundException;

    public Cart findCartByUserId(Long userId) throws Exception, CartNotFoundException;

    Cart clearCart(Long userId) throws CartNotFoundException;

}
