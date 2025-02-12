package com.Food.Ordering.System.controller;

import com.Food.Ordering.System.entity.Cart;
import com.Food.Ordering.System.entity.CartItem;
import com.Food.Ordering.System.exception.CartNotFoundException;
import com.Food.Ordering.System.exception.CartItemNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;
import com.Food.Ordering.System.service.CartService;
import com.Food.Ordering.System.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    // Add Product to Cart
    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(
            @RequestBody CartItem cartItem,
            @RequestHeader("Authorization") String jwt) {

        try {
            String email = jwtUtil.extractUsername(jwt);
            CartItem item = cartService.addProductToCart(cartItem, email);
            return new ResponseEntity<>(item, HttpStatus.CREATED);
        } catch (CartNotFoundException | CartItemNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception | UserNotFoundException e) {
            return new ResponseEntity<>("Something went wrong while adding the product to the cart.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update Cart Item Quantity
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<?> updateCart(
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {

        try {
            CartItem item = cartService.updateCart(cartItemId, quantity);
            return new ResponseEntity<>(item, HttpStatus.OK);
        } catch (CartItemNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception | CartNotFoundException e) {
            return new ResponseEntity<>("Error updating cart item quantity.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Remove Product from Cart
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeProductFromCart(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt) {

        try {
            Cart cart = cartService.removeProductToCart(productId, jwt);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (CartNotFoundException | CartItemNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception | UserNotFoundException e) {
            return new ResponseEntity<>("Error removing product from cart.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Clear Entire Cart
    @PutMapping("/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        try {
            Cart cart = cartService.clearCart(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (CartNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error clearing the cart.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Calculate Total Price of the Cart
    @GetMapping("/total/{cartId}")
    public ResponseEntity<?> calculateTotalPrice(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.findCartById(cartId);
            Long totalPrice = cartService.calculateCartTotals(cart);
            return new ResponseEntity<>(totalPrice, HttpStatus.OK);
        } catch (CartNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error calculating total price.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Cart by User ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findCartByUserId(@PathVariable Long userId) {
        try {
            Cart cart = cartService.findCartByUserId(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (CartNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving cart.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get Cart by Cart ID
    @GetMapping("/{cartId}")
    public ResponseEntity<?> findCartById(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.findCartById(cartId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (CartNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error retrieving cart.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
