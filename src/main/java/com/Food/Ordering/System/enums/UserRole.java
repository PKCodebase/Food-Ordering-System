package com.Food.Ordering.System.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    CUSTOMER("Customer"),
    ADMIN("Admin"),
    RESTAURANT_OWNER("Restaurant Owner");

    private String value;

    UserRole(String  value) {
        this.value = value;

    }
    UserRole() {
    }

}
