package com.Food.Ordering.System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;

    private Long totalPrice;

    private List<String> ingredients;

    @ManyToOne
    @JoinColumn(name ="food_id")
    private Food food;

    @ManyToOne
    @JoinColumn(name= "order_id")
    private Order order;

}
