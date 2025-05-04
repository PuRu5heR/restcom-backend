package by.restcom.userservice.services;

import by.restcom.userservice.dao.RefreshToken;
import by.restcom.userservice.dao.User;
import by.restcom.userservice.exceptions.NotFoundException;
import by.restcom.userservice.model.Role;
import by.restcom.userservice.repository.IRefreshTokenRepository;
import by.restcom.userservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenService {
    private final IRefreshTokenRepository iRefreshTokenRepository;
    private final AuthService authService;
    private final IUserRepository iUserRepository;

    public List<RefreshToken> findUserRefreshTokens() {
        User userFromDB = iUserRepository.findByLogin(authService.getAuthentication().getPrincipal().toString());

        return iRefreshTokenRepository.findRefreshTokenByUserId(userFromDB);
    }

    public List<RefreshToken> findUserRefTokensByAdmin(UUID id) {
        User userFromDB = iUserRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        return iRefreshTokenRepository.findRefreshTokenByUserId(userFromDB);
    }

    public ResponseEntity<?> deleteById(UUID id) {
        Map<Object, Object> response = new HashMap<>();
        RefreshToken refreshTokenFromDB = iRefreshTokenRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Refresh token not found"));
        User userFromDB = iUserRepository.findByLogin(authService.getAuthentication().getPrincipal().toString());

        if (userFromDB.equals(refreshTokenFromDB.getUserId()) || userFromDB.getRole().equals(Role.ADMIN)) {
            iRefreshTokenRepository.deleteById(id);
            response.put("delete_refresh_token", "true");
            log.info("Delete refresh token with id = {} -> {}", id, authService.getAuthentication().getPrincipal());

            return ResponseEntity.ok(response);
        }

        response.put("delete_refresh_token", "false");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
