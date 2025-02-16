package com.Food.Ordering.System.controller;


import com.Food.Ordering.System.entity.Order;
import com.Food.Ordering.System.entity.User;
import com.Food.Ordering.System.exception.CartNotFoundException;
//import com.Food.Ordering.System.service.AuthService;
import com.Food.Ordering.System.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

//    @Autowired
//    private AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order, @AuthenticationPrincipal User user) throws CartNotFoundException, Exception {
        String response = orderService.createOrder(order, user);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestParam String orderStatus) {
        String response = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        String response = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getUsersOrders(userId);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<Order>> getRestaurantOrders(@PathVariable Long restaurantId) {
        List<Order> orders = orderService.getRestaurantsOrders(restaurantId);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.findOrderById(orderId);
        return ResponseEntity.ok(order);
    }



}
