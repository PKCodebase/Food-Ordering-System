package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.Cart;
import com.Food.Ordering.System.entity.CartItem;
import com.Food.Ordering.System.entity.Food;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.CartNotFoundException;
import com.Food.Ordering.System.exception.CartItemNotFoundException;
import com.Food.Ordering.System.exception.FoodNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;
import com.Food.Ordering.System.repository.CartItemRepository;
import com.Food.Ordering.System.repository.CartRepository;
import com.Food.Ordering.System.service.CartService;
import com.Food.Ordering.System.service.FoodService;
import com.Food.Ordering.System.service.UserService;
import com.Food.Ordering.System.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public CartItem addProductToCart(CartItem cartItemDto, String email) throws CartNotFoundException, UserNotFoundException {
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }

        Food food = foodService.findFoodById(cartItemDto.getFood().getId());
        if (food == null) {
            throw new FoodNotFoundException("Food item not found with ID: " + cartItemDto.getFood().getId());
        }

        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for user: " + email);
        }

        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + cartItemDto.getQuantity();
                cartItem.setQuantity(newQuantity);
                cartItem.setTotalPrice(cartItem.getFood().getPrice() * newQuantity);
                return cartItemRepository.save(cartItem);
            }
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setFood(food);
        newCartItem.setQuantity(cartItemDto.getQuantity());
        newCartItem.setIngredients(cartItemDto.getIngredients());
        newCartItem.setTotalPrice(cartItemDto.getQuantity() * food.getPrice());
        newCartItem.setCart(cart);

        return cartItemRepository.save(newCartItem);
    }

    @Override
    public CartItem updateCart(Long cartItemId, int quantity) throws CartNotFoundException {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("No item found in your cart with ID: " + cartItemId));

        cartItem.setQuantity(quantity);
        cartItem.setTotalPrice(cartItem.getFood().getPrice() * quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeProductToCart(Long productId, String jwt) throws CartNotFoundException, UserNotFoundException {
        String email = jwtUtil.extractUsername(jwt);
        User user = userService.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }

        Cart cart = cartRepository.findByCustomerId(user.getId());
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for user: " + email);
        }

        CartItem cartItem = cartItemRepository.findById(productId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found with ID: " + productId));

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotals(Cart cartId) throws CartNotFoundException {
        Cart cart = cartRepository.findById(cartId.getId())
                .orElseThrow(() -> new CartNotFoundException("No items in your cart"));

        return cart.getCartItems().stream()
                .mapToLong(cartItem -> cartItem.getFood().getPrice() * cartItem.getQuantity())
                .sum();
    }

    @Override
    public Cart findCartById(Long id) throws CartNotFoundException {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + id));
    }

    @Override
    public Cart findCartByUserId(Long userId) throws CartNotFoundException {
        Cart cart = cartRepository.findByCustomerId(userId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for user ID: " + userId);
        }
        return cart;
    }

    @Override
    public Cart clearCart(Long userId) throws CartNotFoundException {
        Cart cart = cartRepository.findByCustomerId(userId);
        if (cart == null) {
            throw new CartNotFoundException("Cart not found for user ID: " + userId);
        }
        cart.getCartItems().clear();
        return cartRepository.save(cart);
    }
}
