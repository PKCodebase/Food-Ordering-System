package com.Food.Ordering.System.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private Double totalPrice; // Total price (quantity * price)

    // ✅ Update price & totalPrice whenever quantity is set
    public void updatePrice() {
        if (food != null && food.getPrice() != null) {
            this.price = Double.valueOf(food.getPrice());
            this.totalPrice = this.price * this.quantity;
        }
    }

    // ✅ Call updatePrice() whenever setting quantity
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        updatePrice();
    }

    // ✅ Call updatePrice() whenever setting food
    public void setFood(Food food) {
        this.food = food;
        updatePrice();
    }


    @PrePersist
    @PreUpdate
    public void calculatePrice() {
        updatePrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Food getFood() {
        return food;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
