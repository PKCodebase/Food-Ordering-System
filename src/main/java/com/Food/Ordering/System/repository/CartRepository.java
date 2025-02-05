package com.Food.Ordering.System.repository;

import com.Food.Ordering.System.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long>{

    Cart findByCustomerId(Long userId);

    Optional<Cart> findById(Cart cartId);
}
