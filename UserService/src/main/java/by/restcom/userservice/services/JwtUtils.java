package by.restcom.userservice.services;

import by.restcom.userservice.model.JwtAuthentication;
import by.restcom.userservice.model.Role;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRole(getRoles(claims));
        jwtInfoToken.setName(claims.get("name", String.class));
        jwtInfoToken.setId(claims.get("id", UUID.class));
        jwtInfoToken.setUsername(claims.getSubject());

        return jwtInfoToken;
    }

    private static Role getRoles(Claims claims) {
        final String roles = claims.get("roles", String.class);
        return Enum.valueOf(Role.class, roles);
    }
}
