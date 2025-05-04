package by.restcom.userservice.dao;

import by.restcom.userservice.model.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.RefreshTokenView.Get.class)
    @Column(name = "id")
    private Long id;

    @JsonView(Views.RefreshTokenView.Put.class)
    @Column(name = "name")
    private String name;

    @JsonView(Views.RefreshTokenView.Get.class)
    @Column(name = "user_agent")
    public String userAgent;

    @JsonView(Views.RefreshTokenView.Get.class)
    @Column(name = "ip_address")
    public String ipAddress;

    @JsonView(Views.RefreshTokenView.Get.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @JsonView(Views.UserView.Get.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss")
    @Column(name = "created_ts")
    private LocalDateTime createdTs;

    @JsonView(Views.UserView.Get.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH-mm-ss")
    @Column(name = "updated_ts")
    private LocalDateTime updatedTs;
}