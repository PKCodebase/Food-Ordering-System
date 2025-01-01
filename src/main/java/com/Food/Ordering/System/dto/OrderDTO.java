package com.Food.Ordering.System.dto;

import com.Food.Ordering.System.entity.Address;
import lombok.Data;

@Data
public class OrderDTO {

    private Long restaurantId;

    private Address deliveryAddress;
}
