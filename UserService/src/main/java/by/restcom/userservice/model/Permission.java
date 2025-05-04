package by.restcom.userservice.model;

import lombok.Getter;

@Getter
public enum Permission {
    USERS_READ("users:read"),
    USERS_WRITE("users:write"),
    TABLES_READ("tables:read"),
    TABLES_WRITE("tables:write"),
    BOOKINGS_READ("bookings:read"),
    BOOKINGS_WRITE("bookings:write"),
    MENU_READ("menu:read"),
    MENU_WRITE("menu:write"),
    ORDERS_READ("orders:read"),
    ORDERS_WRITE("orders:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

}
