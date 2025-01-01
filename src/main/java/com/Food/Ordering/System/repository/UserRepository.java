package com.Food.Ordering.System.repository;

import com.Food.Ordering.System.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{


    Optional<User> findByEmail(String email) ;

    Optional<User> findByPhone(String phone);

}
