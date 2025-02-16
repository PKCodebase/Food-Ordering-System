package com.Food.Ordering.System.service.impl;

import com.Food.Ordering.System.entity.Cart;
import com.Food.Ordering.System.entity.CartItem;
import com.Food.Ordering.System.entity.Food;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.CartItemNotFoundException;
import com.Food.Ordering.System.exception.CartNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;
import com.Food.Ordering.System.repository.CartItemRepository;
import com.Food.Ordering.System.repository.CartRepository;
import com.Food.Ordering.System.repository.FoodRepository;
import com.Food.Ordering.System.repository.UserRepository;
import com.Food.Ordering.System.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartItemRepository cartItemRepository;
    private final FoodRepository foodRepository;

    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository,
                           FoodRepository foodRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.cartItemRepository = cartItemRepository;
        this.foodRepository = foodRepository;
    }

    @Override
    public CartItem addProductToCart(Long userId, Food food, Integer quantity)
            throws CartNotFoundException, UserNotFoundException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Cart cart = cartRepository.findByCustomer_Id(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCustomer(user);
                    newCart.setCartItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });

        if (cart.getCartItems() == null) {
            cart.setCartItems(new ArrayList<>());
        }

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getFood().getId().equals(food.getId()))
                .findFirst();

        CartItem cartItem;
        if (existingCartItem.isPresent()) {
            cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setFood(food);
            cartItem.setQuantity(quantity);
            cart.getCartItems().add(cartItem);
        }

        // ✅ Ensure price is updated
        cartItem.updatePrice();
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

        return cartItem;
    }

    @Override
    public CartItem updateCartItem(Long cartItemId, Integer quantity) throws CartItemNotFoundException {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException("Cart item not found"));
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Cart removeProductFromCart(Long userId, Long foodId) throws CartNotFoundException, UserNotFoundException {
        Cart cart = cartRepository.findByCustomer_Id(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        cart.getCartItems().removeIf(item -> item.getFood().getId().equals(foodId));
        return cartRepository.save(cart);
    }

    @Override
    public Long calculateCartTotal(Long userId) throws CartNotFoundException {
        Cart cart = cartRepository.findByCustomer_Id(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));

        return cart.getCartItems().stream()
                .mapToLong(item -> item.getFood().getPrice() * item.getQuantity())
                .sum();
    }

    @Override
    public Cart getCart(Long userId) throws UserNotFoundException, CartNotFoundException {
        return cartRepository.findByCustomer_Id(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
    }

    @Override
    public Cart clearCart(Long userId) throws CartNotFoundException {
        Cart cart = cartRepository.findByCustomer_Id(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
        cart.getCartItems().clear();
        return cartRepository.save(cart);
    }
}
