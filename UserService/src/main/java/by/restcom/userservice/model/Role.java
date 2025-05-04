package by.restcom.userservice.model;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {
    ADMIN(Set.of(Permission.USERS_READ, Permission.USERS_WRITE, Permission.TABLES_READ, Permission.TABLES_WRITE,
            Permission.BOOKINGS_READ, Permission.BOOKINGS_WRITE, Permission.MENU_READ, Permission.MENU_WRITE,
            Permission.ORDERS_READ, Permission.ORDERS_WRITE)),
    TECHNICAL_SUPPORT(Set.of(Permission.USERS_READ, Permission.TABLES_READ, Permission.BOOKINGS_READ,
            Permission.MENU_READ, Permission.ORDERS_READ)),
    GUEST(Set.of(Permission.MENU_READ)),
    CLIENT(Set.of(Permission.TABLES_READ, Permission.BOOKINGS_READ, Permission.MENU_READ, Permission.ORDERS_READ)),
    WAITER(Set.of(Permission.USERS_READ, Permission.BOOKINGS_READ, Permission.BOOKINGS_WRITE, Permission.MENU_READ,
            Permission.ORDERS_READ, Permission.ORDERS_WRITE)),
    HALL_ADMIN(Set.of(Permission.USERS_READ, Permission.TABLES_READ, Permission.BOOKINGS_READ, Permission.BOOKINGS_WRITE,
            Permission.MENU_READ, Permission.MENU_WRITE, Permission.ORDERS_READ, Permission.ORDERS_WRITE)),
    COOK(Set.of(Permission.TABLES_READ, Permission.MENU_READ, Permission.MENU_WRITE, Permission.ORDERS_READ,
            Permission.ORDERS_WRITE)),
    BARMAN(Set.of(Permission.TABLES_READ, Permission.MENU_READ, Permission.MENU_WRITE, Permission.ORDERS_READ,
            Permission.ORDERS_WRITE)),
    MANAGER(Set.of(Permission.USERS_READ, Permission.TABLES_READ, Permission.TABLES_WRITE, Permission.BOOKINGS_READ,
            Permission.MENU_READ, Permission.MENU_WRITE, Permission.ORDERS_READ)),
    OWNER(Set.of(Permission.USERS_READ, Permission.TABLES_READ, Permission.TABLES_WRITE, Permission.BOOKINGS_READ,
            Permission.MENU_READ, Permission.MENU_WRITE, Permission.ORDERS_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
