package com.Food.Ordering.System.service;

import com.Food.Ordering.System.entity.Order;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.CartNotFoundException;
import com.Food.Ordering.System.exception.OrderNotFoundException;
import com.Food.Ordering.System.exception.UserNotFoundException;

import java.util.List;

public interface OrderService {
    String createOrder(Order order, User user) throws Exception, UserNotFoundException, CartNotFoundException;
    String updateOrder(Long orderId, String orderStatus);
    String cancelOrder(Long orderId);
    List<Order> getUsersOrders(Long userId);
    List<Order> getRestaurantsOrders(Long restaurantId,String orderStatus);
    Order findOrderById(Long orderId) throws OrderNotFoundException;
}
