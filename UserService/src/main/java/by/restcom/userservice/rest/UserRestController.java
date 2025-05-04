package by.restcom.userservice.rest;

import by.restcom.userservice.dao.User;
import by.restcom.userservice.exceptions.NotFoundException;
import by.restcom.userservice.model.Views;
import by.restcom.userservice.repository.IUserRepository;
import by.restcom.userservice.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserRepository iUserRepository;
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('users:write')")
    @JsonView(Views.UserWithRefreshTokenView.class)
    @GetMapping
    public List<User> getAll() {
        return iUserRepository.findAll();
    }

    @PreAuthorize("hasAnyAuthority('users:write')")
    @JsonView(Views.UserWithRefreshTokenView.class)
    @GetMapping("{id}")
    public User getOne(@PathVariable UUID id) {
        return iUserRepository.findById(id).orElseThrow(() -> new NotFoundException("User nod found"));
    }

    @PreAuthorize("hasAnyAuthority('users:write')")
    @JsonView(Views.UserWithRefreshTokenView.class)
    @GetMapping("/search/{text}")
    public List<User> search(@PathVariable String text) {
        return userService.searchUser(text);
    }

    @PreAuthorize("hasAnyAuthority('users:write')")
    @JsonView(Views.UserWithRefreshTokenView.class)
    @GetMapping("/search-login/{login}")
    public List<User> getByLogin(@PathVariable String login) {
        return iUserRepository.findByLoginContainingIgnoreCase(login);
    }

    @PreAuthorize("hasAnyAuthority('users:write')")
    @JsonView(Views.UserWithRefreshTokenView.class)
    @GetMapping("/search-name/{name}")
    public List<User> getByName(@PathVariable String name) {
        return iUserRepository.findByNameContainingIgnoreCase(name);
    }

    @PreAuthorize("hasAnyAuthority('users:write')")
    @JsonView(Views.UserWithRefreshTokenView.class)
    @GetMapping("/search-surname/{surname}")
    public List<User> getBySurname(@PathVariable String surname) {
        return iUserRepository.findBySurnameContainingIgnoreCase(surname);
    }

    @PreAuthorize("hasAnyAuthority('users:write')")
    @JsonView(Views.UserWithRefreshTokenView.class)
    @GetMapping("/search-email/{email}")
    public List<User> getByEmail(@PathVariable String email) {
        return iUserRepository.findByEmailContainingIgnoreCase(email);
    }

    @PreAuthorize("hasAnyAuthority('users:write')")
    @JsonView(Views.UserView.Get.class)
    @PostMapping
    public User create(@RequestBody @JsonView(Views.UserView.Post.class) User user) {
        return userService.createUser(user);
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority('users:write')")
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable UUID id,
                                    @RequestBody @JsonView(Views.UserView.Put.class) User user) {
        return userService.updateUser(id, user);
    }

    @PreAuthorize("hasAnyAuthority('users:write')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return userService.deleteUserById(id);
    }
}
