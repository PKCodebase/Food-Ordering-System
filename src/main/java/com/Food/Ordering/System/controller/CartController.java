package com.Food.Ordering.System.controller;


import com.Food.Ordering.System.entity.Cart;
import com.Food.Ordering.System.entity.CartItem;
import com.Food.Ordering.System.exception.CartNotFoundException;
import com.Food.Ordering.System.service.CartService;
import com.Food.Ordering.System.service.UserService;
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
    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(
            @RequestBody CartItem cartItem,
            @RequestHeader("Authorization")  String jwt) throws CartNotFoundException {

        try {
            CartItem item = cartService.addProductToCart(cartItem, jwt);
            return new ResponseEntity<>(item, HttpStatus.CREATED);
        }catch(CartNotFoundException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("Something is missing",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCart(@PathVariable Long cartItemId,@RequestParam int quantity) throws CartNotFoundException {
       try{
           CartItem item = cartService.updateCart(cartItemId, quantity);
           return new ResponseEntity<>(item, HttpStatus.OK);
       }catch (CartNotFoundException ex){
           return  new ResponseEntity<>("No any item in your cart",HttpStatus.NOT_FOUND);
       }catch (Exception e){
           return new ResponseEntity<>("Cart item not updating",HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?>removeProductToCart(@PathVariable Long productId
    ,@RequestHeader("Authorization") String jwt) throws CartNotFoundException{
        try{
            Cart item = cartService.removeProductToCart(productId,jwt);
            return new ResponseEntity<>(item, HttpStatus.OK);
        }catch (CartNotFoundException ex){
            return new ResponseEntity<>("No any item in Cart", HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>("Cart item not removed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Long userId){
        try{
            Cart cart = cartService.clearCart(userId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Cart not cleared", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/total/{userId}")
    public ResponseEntity<?> calculateTotalPrice(@PathVariable Long cartId){
        try{
            Cart cart = cartService.findCartById(cartId);
            Long totalPrice = cartService.calculateCartTotals(cart);
            return new ResponseEntity<>(totalPrice, HttpStatus.OK);
        }catch (Exception | CartNotFoundException e){
            return new ResponseEntity<>("Total price not calculated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findCartById


}
