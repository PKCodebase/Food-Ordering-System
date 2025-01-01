package com.Food.Ordering.System.repository;

import com.Food.Ordering.System.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
}
