package com.Food.Ordering.System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Long Price;

    // Each Food item can have many OrderItem
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "food")
    private List<OrderItem> orderItems;

    //Each Food item can belong to one Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private boolean available;

    //Many Food items can belong to one Restaurant.
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private boolean isVegetarian;

    private boolean isSeasonal;

    //Each Food item can have many IngredientsItem
    @ManyToMany
    @JoinTable(
            name = "food_ingredients",// It will contain the foreign keys to both Food and IngredientsItem.
            joinColumns = @JoinColumn(name = "food_id"),// Specifies the foreign key for the Food entity.
            inverseJoinColumns = @JoinColumn(name = "ingredients_item_id") //Specifies the foreign key for the IngredientsItem entity.
    )
    private List<IngredientsItem> ingredientsItems;

    private Date  creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return Price;
    }

    public void setPrice(Long price) {
        Price = price;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public boolean isSeasonal() {
        return isSeasonal;
    }

    public void setSeasonal(boolean seasonal) {
        isSeasonal = seasonal;
    }

    public List<IngredientsItem> getIngredientsItems() {
        return ingredientsItems;
    }

    public void setIngredientsItems(List<IngredientsItem> ingredientsItems) {
        this.ingredientsItems = ingredientsItems;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
