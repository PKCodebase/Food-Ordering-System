package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.entity.Cart;
import com.Food.Ordering.System.entity.CartItem;
import com.Food.Ordering.System.entity.Food;
import com.Food.Ordering.System.exception.CartNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;
import com.Food.Ordering.System.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add/{userId}/{foodId}/{quantity}")
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long userId,
                                                     @PathVariable Long foodId,
                                                     @PathVariable Integer quantity)
            throws UserNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(cartService.addProductToCart(userId, new Food(foodId), quantity));
    }

    @PutMapping("/update/{cartItemId}/{quantity}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId,
                                                   @PathVariable Integer quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(cartItemId, quantity));
    }

    @DeleteMapping("/remove/{userId}/{foodId}")
    public ResponseEntity<Cart> removeProductFromCart(@PathVariable Long userId,
                                                      @PathVariable Long foodId) throws UserNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(cartService.removeProductFromCart(userId, foodId));
    }

    @GetMapping("/total/{userId}")
    public ResponseEntity<Double> calculateCartTotal(@PathVariable Long userId) throws CartNotFoundException {
        return ResponseEntity.ok(cartService.calculateCartTotal(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long userId) throws UserNotFoundException, CartNotFoundException {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Cart> clearCart(@PathVariable Long userId) throws CartNotFoundException {
        return ResponseEntity.ok(cartService.clearCart(userId));
    }
}
