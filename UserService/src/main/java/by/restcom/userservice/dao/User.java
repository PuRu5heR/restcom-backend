package by.restcom.userservice.dao;

import by.restcom.userservice.model.Role;
import by.restcom.userservice.model.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonView(Views.UserView.Get.class)
    @Column(name = "id")
    private UUID id;

    @JsonView({Views.UserView.Get.class,
            Views.UserView.Put.class})
    @Column(name = "login")
    private String login;

    @JsonView({Views.UserView.Put.class, Views.UserView.Profile.class})
    @Column(name = "password")
    private String password;

    @JsonView({Views.UserView.Get.class})
    @Column(name = "telegram_id")
    private Long telegramId;

    @JsonView({Views.UserView.Get.class, Views.UserView.Put.class})
    @Column(name = "telegram_username")
    private String telegramUsername;

    @JsonView({Views.UserView.Get.class, Views.UserView.Post.class, Views.UserView.Put.class, Views.UserView.Profile.class})
    @Column(name = "surname", nullable = false)
    private String surname;

    @JsonView({Views.UserView.Get.class, Views.UserView.Post.class, Views.UserView.Put.class, Views.UserView.Profile.class})
    @Column(name = "name", nullable = false)
    private String name;

    @JsonView({Views.UserView.Get.class, Views.UserView.Post.class, Views.UserView.Put.class, Views.UserView.Profile.class})
    @Column(name = "patronymic")
    private String patronymic;

    @JsonView({Views.UserView.Get.class, Views.UserView.Put.class, Views.UserView.Profile.class})
    @Column(name = "email")
    private String email;

    @JsonView({Views.UserView.Get.class, Views.UserView.Put.class, Views.UserView.Profile.class})
    @Column(name = "phone")
    private String phone;

    @JsonView({Views.UserView.Get.class, Views.UserView.Post.class, Views.UserView.Put.class})
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonView({Views.UserView.Get.class, Views.UserView.Put.class})
    @Column(name = "blocked", nullable = false)
    private Boolean blocked;

    @JsonView({Views.UserView.Get.class, Views.UserView.Put.class})
    @Column(name = "protection_from_deletion", nullable = false)
    private Boolean protectionFromDeletion;

    @JsonView(Views.UserView.Get.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss")
    @Column(name = "created_ts")
    private LocalDateTime createdTs;

    @JsonView(Views.UserView.Get.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss")
    @Column(name = "updated_ts")
    private LocalDateTime updatedTs;

    @JsonView(Views.UserWithRefreshTokenView.class)
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RefreshToken> refreshTokens;
}
