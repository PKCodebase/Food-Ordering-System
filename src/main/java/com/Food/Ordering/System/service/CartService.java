package com.Food.Ordering.System.service;

import com.Food.Ordering.System.dto.CartItemDTO;
import com.Food.Ordering.System.entity.Cart;

public interface CartService {

    String  addProductToCart(CartItemDTO cartItemDTO, String jwt);

    String updateCart(Long cartItemId, int quantity);

    String removeProductFromCart(Long productId, String jwt);

    Long calculateCartTotals(Long id);

    Cart findCartByUserId(Long userId);

    Cart findCartByCartId(Long cartId);

    String clearCart(Long userId);

}
