package by.restcom.userservice.services;

import by.restcom.userservice.config.PasswordEncoderConfig;
import by.restcom.userservice.dao.User;
import by.restcom.userservice.exceptions.NotFoundException;
import by.restcom.userservice.repository.IRefreshTokenRepository;
import by.restcom.userservice.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository iUserRepository;
    private final PasswordEncoderConfig passwordEncoderConfig;
    private final AuthService authService;
    private final IRefreshTokenRepository iRefreshTokenRepository;

    public User createUser(User user) {
        user.setCreatedTs(LocalDateTime.now());
        user.setUpdatedTs(LocalDateTime.now());
        user.setBlocked(false);
        user.setProtectionFromDeletion(false);
        iUserRepository.save(user);
        log.warn("The user {} has ben created. -> {}", user.getLogin(),
                authService.getAuthentication().getPrincipal());

        return user;
    }

    public ResponseEntity<?> deleteUserById(UUID id) {
        Map<Object, Object> response = new HashMap<>();
        User userFromDB = iUserRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        if (userFromDB == null) {
            response.put("error", "delete user");
            response.put("text", "The user could not be deleted. User with id = " + id + " not found");
            log.warn("The user could not be deleted. User with id = {} not found -> {}",
                    id, authService.getAuthentication().getPrincipal());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else if (userFromDB.getProtectionFromDeletion()){
            response.put("error", "delete user");
            response.put("text", "The user is protected from deletion");

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            iUserRepository.deleteById(id);
            response.put("delete_user", "true");
            log.info("Delete user with id = {} -> {}", id, authService.getAuthentication().getPrincipal());
        }

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> updateUser(UUID id, User user) {
        Map<Object, Object> response = new HashMap<>();
        User userFromDB = iUserRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        if (userFromDB == null) {
            response.put("error", "update user");
            response.put("text", "The user could not be update. User with id " + id + " not found");
            log.warn("The user could not be updated. User with id {} not found -> {}",
                    id, authService.getAuthentication().getPrincipal());

            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        if (user.getEmail() != null) {
            userFromDB.setEmail(user.getEmail());
            log.info("The user's email address with id {} has been updated -> {}",
                    id, authService.getAuthentication().getPrincipal());
        }

        if (user.getPassword() != null) {
            userFromDB.setPassword(passwordEncoderConfig.getPasswordEncoder().encode(user.getPassword()));
            iRefreshTokenRepository.deleteByUserId(userFromDB);
            log.info("The password of the user with the id {} has been updated -> {}",
                    id, authService.getAuthentication().getPrincipal());
        }

        if (user.getName() != null) {
            userFromDB.setName(user.getName());
            log.info("The name of the user with the id {} has been updated -> {}",
                    id, authService.getAuthentication().getPrincipal());
        }

        if (user.getSurname() != null) {
            userFromDB.setSurname(user.getSurname());
            log.info("The surname of the user with the id {} has been updated -> {}",
                    id, authService.getAuthentication().getPrincipal());
        }

        if (user.getPatronymic() != null) {
            userFromDB.setPatronymic(user.getPatronymic());
            log.info("The patronymic of the user with the id {} has been updated -> {}",
                    id, authService.getAuthentication().getPrincipal());
        }

        if (user.getRole() != null) {
            userFromDB.setRole(user.getRole());
            log.info("The role of the user with the id {} has been updated -> {}",
                    id, authService.getAuthentication().getPrincipal());
        }

        if (user.getBlocked() != null) {
            userFromDB.setBlocked(user.getBlocked());

            if (user.getBlocked()) {
                iRefreshTokenRepository.deleteByUserId(userFromDB);
                log.info("The sessions of the user with the id {} has been deleted -> {}",
                        id, authService.getAuthentication().getPrincipal());
            }

            log.info("The blocked of the user with the id {} has been updated -> {}",
                    id, authService.getAuthentication().getPrincipal());
        }

        if (user.getProtectionFromDeletion() != null) {
            userFromDB.setProtectionFromDeletion(user.getProtectionFromDeletion());
            log.info("The protection from deletion of the user with the id {} has been updated -> {}",
                    id, authService.getAuthentication().getPrincipal());
        }

        userFromDB.setUpdatedTs(LocalDateTime.now());
        iUserRepository.save(userFromDB);
        response.put("update_user", "true");

        return ResponseEntity.ok(response);
    }

    public List<User> searchUser(String text) {
        List<User> result = new ArrayList<>();
        result.addAll(iUserRepository.findByLoginContainingIgnoreCase(text));
        result.addAll(iUserRepository.findByNameContainingIgnoreCase(text));
        result.addAll(iUserRepository.findBySurnameContainingIgnoreCase(text));
        result.addAll(iUserRepository.findByPatronymicContainingIgnoreCase(text));
        result.addAll(iUserRepository.findByEmailContainingIgnoreCase(text));

        return result.stream().distinct().collect(Collectors.toList());
    }
}
