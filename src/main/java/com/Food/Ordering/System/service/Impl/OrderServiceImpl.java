package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.*;
import com.Food.Ordering.System.exception.CartNotFoundException;
import com.Food.Ordering.System.exception.OrderNotFoundException;
import com.Food.Ordering.System.exception.RestaurantNotFoundException;
import com.Food.Ordering.System.repository.*;
import com.Food.Ordering.System.service.CartService;
import com.Food.Ordering.System.service.OrderService;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

//    @Autowired
//    private AuthRepository authRepository;


    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public String createOrder(Order order, User user) throws Exception, CartNotFoundException {
        // Save the delivery address if not already in user's address list
        Address shippingAddress = order.getDeliveryAddress();
        Address savedAddress = addressRepository.save(shippingAddress);
        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }

        // Get the restaurant based on the order
        Restaurant restaurant = restaurantService.getRestaurantById(order.getId());
        if (restaurant == null) {
            throw new RestaurantNotFoundException("Restaurant not found: ");
        }

        // Create the order object
        Order createOrder = new Order();
        createOrder.setCustomer(user);
        createOrder.setRestaurant(restaurant);
        createOrder.setDeliveryAddress(savedAddress);
        createOrder.setOrderStatus("PENDING");
        createOrder.setCreatedAt(new Date());

        // Generate order items based on the user's cart
          Cart cart = cartService.findCartById(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
        }

        // Calculate total price
        Long totalPrice = cartService.calculateCartTotals(cart);
        createOrder.setTotalPrice(totalPrice);
        createOrder.setOrderItems(orderItems);

        // Save the order
        Order savedOrder = orderRepository.save(createOrder);
        restaurant.getOrders().add(savedOrder);
        return "Order created successfully";
    }

    @Override
    public String updateOrderStatus(Long orderId, String orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        return "Order status updated successfully";
    }

    @Override
    public String cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        order.setOrderStatus("CANCELED");
        orderRepository.save(order);
        return "Order canceled successfully";
    }

    @Override
    public List<Order> getUsersOrders(Long userId) {
        List<Order> orders = orderRepository.findByCustomerId(userId);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for user ID: " + userId);
        }
        return orders;
    }

    @Override
    public List<Order> getRestaurantsOrders(Long restaurantId) {
        List<Order> restaurantOrders = orderRepository.findByRestaurantId(restaurantId);
        if (restaurantOrders.isEmpty()) {
            throw new OrderNotFoundException("No orders found for restaurant with ID: " + restaurantId);
        }
        return restaurantOrders;
    }

    @Override
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }
}
