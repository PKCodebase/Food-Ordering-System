package com.Food.Ordering.System.repository;

import com.Food.Ordering.System.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>{
}
