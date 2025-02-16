package com.Food.Ordering.System.service.Impl;

import com.Food.Ordering.System.entity.*;
import com.Food.Ordering.System.exception.CartNotFoundException;
import com.Food.Ordering.System.exception.OrderNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;
import com.Food.Ordering.System.repository.*;
import com.Food.Ordering.System.service.CartService;
import com.Food.Ordering.System.service.OrderService;
import com.Food.Ordering.System.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;

    @Override
    public String createOrder(Order order, User user) throws Exception, UserNotFoundException, CartNotFoundException {
        if (user == null || user.getId() == null) {
            throw new UserNotFoundException("User not found.");
        }

        if (order.getDeliveryAddress() == null) {
            throw new IllegalArgumentException("Delivery address is required.");
        }


        Long restaurantId = order.getRestaurant().getId();
        if (restaurantId == null) {
            throw new IllegalArgumentException("Restaurant ID is required.");
        }
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);

        // Save address
        Address deliveryAddress = order.getDeliveryAddress();
        deliveryAddress.setUser(user);
        Address savedAddress = addressRepository.save(deliveryAddress);

        if (!user.getAddresses().contains(savedAddress)) {
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }


        Order createdOrder = new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);


        Cart cart = cartService.getCart(user.getId());

        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getTotalPrice());
            return orderItemRepository.save(orderItem);
        }).collect(Collectors.toList());

        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cartService.calculateCartTotal(cart.getId()));

        orderRepository.save(createdOrder);
        return "Order successfully created with ID: " + createdOrder.getId();
    }

    @Override
    public String updateOrder(Long orderId, String orderStatus) {
        Order order = findOrderById(orderId);

        // Validate order status
        if (!List.of("OUT_FOR_DELIVERY", "DELIVERED", "COMPLETED", "PENDING").contains(orderStatus)) {
            throw new IllegalArgumentException("Invalid order status. Allowed values: PENDING, OUT_FOR_DELIVERY, DELIVERED, COMPLETED.");
        }

        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        return "Order status updated to: " + orderStatus;
    }

    @Override
    public String cancelOrder(Long orderId) {
        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);
        return "Order with ID " + orderId + " has been canceled.";
    }

    @Override
    public List<Order> getUsersOrders(Long userId) {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrders(Long restaurantId, String orderStatus) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);

        return (orderStatus != null)
                ? orders.stream().filter(order -> order.getOrderStatus().equalsIgnoreCase(orderStatus)).collect(Collectors.toList())
                : orders;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderNotFoundException {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + orderId + " not found."));
    }
}
