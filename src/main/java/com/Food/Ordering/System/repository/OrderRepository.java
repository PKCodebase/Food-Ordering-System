package com.Food.Ordering.System.repository;


import com.Food.Ordering.System.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{
    List<Order> findByRestaurantId(Long restaurantId);

    List<Order> findByCustomerId(Long userId);
}
