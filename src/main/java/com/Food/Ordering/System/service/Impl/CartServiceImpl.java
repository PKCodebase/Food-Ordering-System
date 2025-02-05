package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.Cart;
import com.Food.Ordering.System.entity.CartItem;
import com.Food.Ordering.System.entity.Food;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.CartNotFoundException;
import com.Food.Ordering.System.repository.CartItemRepository;
import com.Food.Ordering.System.repository.CartRepository;
import com.Food.Ordering.System.service.AuthService;
import com.Food.Ordering.System.service.CartService;
import com.Food.Ordering.System.service.FoodService;
import com.Food.Ordering.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Override
    public String addProductToCart(CartItem cartItemDto, String email) throws CartNotFoundException {
        User user = userService.getUserByEmail(email);
        Food food = foodService.findFoodById(user.getId());
        Cart cart = cartRepository.findByCustomerId(user.getId());
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItemDto.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + cartItem.getQuantity();
                return updateCart(cartItem.getId(), newQuantity);
            }
        }

        CartItem newcartItem = new CartItem();
        newcartItem.setFood(food);
        newcartItem.setQuantity(cartItemDto.getQuantity());
        newcartItem.setIngredients(cartItemDto.getIngredients());
        newcartItem.setTotalPrice(cartItemDto.getQuantity() + food.getPrice());
        newcartItem.setCart(cart);

        CartItem savedCartItem = cartItemRepository.save(newcartItem);
        cart.getCartItems().add(savedCartItem);
        cartRepository.save(cart);
        return "Product added to cart successfully";
    }



    @Override
    public String updateCart(Long cartItemId, int quantity) throws CartNotFoundException {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if(cartItem.isEmpty()){
            throw new CartNotFoundException("No any item in your cart: " + cartItemId);
        }
        CartItem item = cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);
        cartItemRepository.save(item);
        return "Added Successfully";
    }

    @Override
    public Cart removeProductToCart(Long productId, String jwt) throws CartNotFoundException {
        User user = userService.getUserByEmail(jwt);
        Cart item = cartRepository.findByCustomerId(user.getId());

        Optional<CartItem> cartItem = cartItemRepository.findById(productId);
        if(cartItem.isEmpty()){
            throw new CartNotFoundException("No any item in your cart: " + productId);
        }
        CartItem cartItem1 = cartItem.get();
        item.getCartItems().remove(cartItem1);
        return  cartRepository.save(item);
    }

    @Override
    public Long calculateCartTotals(Cart id) throws CartNotFoundException {
        Optional<Cart> optionalCart = cartRepository.findById(id);
        if (optionalCart.isEmpty()) {
            throw new CartNotFoundException("No any item in your cart"); // Return 0 if cart does not exist
        }
        Cart cart = optionalCart.get();
        return cart.getCartItems().stream()
                .mapToLong(cartItem -> cartItem.getFood().getPrice() * cartItem.getQuantity())
                .sum();
    }

    @Override
    public Cart findCartById(Long id) throws Exception {
        return null;
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        return null;
    }


    @Override
    public String clearCart(Long userId) {
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.getCartItems().clear();
        cartRepository.save(cart);
        return  "Cart cleared successfully";
    }
}
