package com.Food.Ordering.System.repository;

import com.Food.Ordering.System.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{
}
