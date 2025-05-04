package by.restcom.userservice.repository;

import by.restcom.userservice.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {
    User findByLogin(String login);
    User findByTelegramId(Long telegramId);
    User findByTelegramUsername(String telegramUsername);
    List<User> findByLoginContainingIgnoreCase(String login);
    List<User> findByTelegramUsernameContainingIgnoreCase(String TelegramUsername);
    List<User> findBySurnameContainingIgnoreCase(String surname);
    List<User> findByNameContainingIgnoreCase(String name);
    List<User> findByPatronymicContainingIgnoreCase(String patronymic);
    List<User> findByEmailContainingIgnoreCase(String email);
}
