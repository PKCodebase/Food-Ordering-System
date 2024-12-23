package com.Food.Ordering.System.entity;

import com.Food.Ordering.System.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.CUSTOMER;
}
