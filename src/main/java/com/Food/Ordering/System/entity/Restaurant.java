package com.Food.Ordering.System.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private String foodType;

    private String openingHours;

    private LocalDateTime registrationDate;

    private boolean open=false;

    //Each restaurant can have many orders.
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    //Many restaurants can be owned by one user.
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false,referencedColumnName = "id")
    private User owner;

    //Each restaurant has exactly one address.
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @Embedded
    private ContactInformation contactInformation;

    @ElementCollection
    @Column(length = 1000)
    private List<String> images;

    //Each restaurant can offer multiple food items
    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Food> foods;

    //Each restaurant  have multiple IngredientsItem
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientsItem> ingredientsItems;

    //Each restaurant  have multiple IngredientsCategory
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IngredientCategory> ingredientCategories;

    //Each restaurant have multiple categories
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Category> categories;
}
