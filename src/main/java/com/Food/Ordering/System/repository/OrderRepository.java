package com.Food.Ordering.System.repository;


import com.Food.Ordering.System.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>{
}
