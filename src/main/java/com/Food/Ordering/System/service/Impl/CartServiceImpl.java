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
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private FoodService foodService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public CartItem addProductToCart(CartItem cartItemDto, String email) throws CartNotFoundException {
        User user = userService.getUserByEmail(email);
        Food food = foodService.findFoodById(cartItemDto.getFood().getId());
        Cart cart = cartRepository.findByCustomerId(user.getId());
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItemDto.getFood().equals(food)) {
                int newQuantity = cartItem.getQuantity() + cartItem.getQuantity();
                cartItem.setQuantity(newQuantity);
                cartItem.setTotalPrice(cartItem.getFood().getPrice() * newQuantity);
//                return updateCart(cartItem.getId(), newQuantity);
            }
        }

        CartItem newcartItem = new CartItem();
        newcartItem.setFood(food);
        newcartItem.setQuantity(cartItemDto.getQuantity());
        newcartItem.setIngredients(cartItemDto.getIngredients());
        newcartItem.setTotalPrice(cartItemDto.getQuantity() * food.getPrice());
        newcartItem.setCart(cart);

        return cartItemRepository.save(newcartItem);

//        CartItem savedCartItem = cartItemRepository.save(newcartItem);
//        cart.getCartItems().add(savedCartItem);
//        cartRepository.save(cart);
//        return "Product added to cart successfully";
    }



    @Override
    public CartItem updateCart(Long cartItemId, int quantity) throws CartNotFoundException {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if(cartItem.isEmpty()){
            throw new CartNotFoundException("No any item in your cart: " + cartItemId);
        }
        CartItem item = cartItem.get();
        item.setQuantity(quantity);
        item.setTotalPrice(item.getFood().getPrice() * quantity);

       return cartItemRepository.save(item);

    }

    @Override
    public Cart removeProductToCart(Long productId, String jwt) throws CartNotFoundException {
        String email = jwtUtil.extractUsername(jwt);
        User user = userService.getUserByEmail(email);

//        User user = userService.getUserByEmail(jwt);
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
    public Long calculateCartTotals(Cart cartId) throws CartNotFoundException {
        Optional<Cart> optionalCart = cartRepository.findById(cartId.getId());
        if (optionalCart.isEmpty()) {
            throw new CartNotFoundException("No any item in your cart"); // Return 0 if cart does not exist
        }
        Cart cart = optionalCart.get();
        return cart.getCartItems().stream()
                .mapToLong(cartItem -> cartItem.getFood().getPrice() * cartItem.getQuantity())
                .sum();
    }

    @Override
    public Cart findCartById(Long id) throws  CartNotFoundException {
        return cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + id));
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        return null;
    }


    @Override
    public Cart clearCart(Long userId) {
        Cart cart = cartRepository.findByCustomerId(userId);
        cart.getCartItems().clear();
        return  cartRepository.save(cart);
    }
}
