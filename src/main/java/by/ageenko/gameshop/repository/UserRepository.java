package by.ageenko.gameshop.repository;

import by.ageenko.gameshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findFirstByName(String username);
}
