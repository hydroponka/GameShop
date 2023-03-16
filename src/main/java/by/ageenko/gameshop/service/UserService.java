package by.ageenko.gameshop.service;

import by.ageenko.gameshop.exception.UserServiceException;
import by.ageenko.gameshop.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    boolean saveFromRegisterForm(User user) throws UserServiceException;
    void save(User user) throws UserServiceException;
    List<User> findAll();
    User findByName(String name);
}
