package com.Food.Ordering.System.entity;

import jakarta.persistence.*;
import jdk.jfr.Category;
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

    @Column(length = 1000)
    @ElementCollection
    public List<String> images;

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
}
