package by.restcom.userservice.repository;

import by.restcom.userservice.dao.RefreshToken;
import by.restcom.userservice.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    List<RefreshToken> findRefreshTokenByUserId(User userId);
    void deleteByUserId(User userId);
}
