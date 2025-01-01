package com.Food.Ordering.System.repository;

import com.Food.Ordering.System.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
}
