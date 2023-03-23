package by.ageenko.gameshop.repository;

import by.ageenko.gameshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByUsername(String username);
    Optional<User> findUserByVerificationCode(String code);
}
