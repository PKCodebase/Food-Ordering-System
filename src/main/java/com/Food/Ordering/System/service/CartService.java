package com.Food.Ordering.System.service;


import com.Food.Ordering.System.entity.Cart;
import com.Food.Ordering.System.entity.CartItem;
import com.Food.Ordering.System.exception.CartNotFoundException;

public interface CartService {

    CartItem  addProductToCart(CartItem cartItem, String email) throws CartNotFoundException;

    CartItem updateCart(Long cartItemId, int quantity) throws CartNotFoundException;

    Cart removeProductToCart(Long productId, String jwt) throws CartNotFoundException;

    Long calculateCartTotals(Cart id) throws CartNotFoundException;

    public Cart findCartById(Long id) throws Exception, CartNotFoundException;

    public Cart findCartByUserId(Long userId) throws Exception;

    Cart clearCart(Long userId);

}
