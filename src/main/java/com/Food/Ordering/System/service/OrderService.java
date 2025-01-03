package com.Food.Ordering.System.service;

import com.Food.Ordering.System.dto.OrderDTO;
import com.Food.Ordering.System.entity.Order;
import com.Food.Ordering.System.entity.User;

import java.util.List;

public interface OrderService {

    String createOrder(OrderDTO orderDTO, User user);

    String updateOrderStatus(Long orderId, String orderStatus);

    String cancelOrder(Long orderId);

    List<Order> getUsersOrders(Long userId);

    List<Order> getRestaurantsOrders(Long restaurantId);

    Order findOrderById(Long orderId);
}
