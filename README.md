Food Ordering System


The Food Ordering System is a Spring Boot-based web application that allows users to browse restaurants, view menus, place orders, and manage their carts. It includes role-based authentication and authorization using JWT security, ensuring a secure and seamless experience for both customers and restaurant owners.

Technologies Used

Backend: Java 17, Spring Boot 3.4.0, Spring Security, JWT Authentication

Database: MySQL

Dependencies: Spring Data JPA, Lombok, Validation, Spring Web, Spring Security

Features

Authentication & Authorization

User registration and login

Role-based access control (User, Admin, Owner)

JWT-based authentication

Restaurant Management

Admin and Owners can add, update, and delete restaurants

Users can view restaurant details

Category & Food Management

Admin and Owners can manage food categories and items

Users can browse food items based on category and restaurant

Cart & Order Management

Users can add and remove items from their cart

Users can place orders

Admin and Owners can manage order statuses

API Endpoints

Authentication

POST /auth/register - Register a new user

POST /auth/login - Authenticate user and generate JWT token

Restaurant Management

POST /restaurant/add - Add a new restaurant (Admin, Owner)

GET /restaurant/{restaurantId} - Get restaurant details

Food & Category Management

POST /food/add - Add food item (Admin, Owner)

GET /food/restaurant/{restaurantId} - Get food items by restaurant

GET /category/{categoryId} - Get category details

Cart & Order Management

POST /cart/add/{userId}/{foodId}/{quantity} - Add item to cart

GET /orders/user/{userId} - Get user orders

Security & Authentication

The application implements JWT-based authentication. Users must include their JWT token in the Authorization header when accessing protected endpoints.


Configure MySQL Database (Update application.properties)
properties
spring.datasource.url=jdbc:mysql://localhost:3306/food_apps
spring.datasource.username=root
spring.datasource.password=root
