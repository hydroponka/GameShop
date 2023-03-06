package by.ageenko.gameshop.service;

import by.ageenko.gameshop.dto.UserDto;
import by.ageenko.gameshop.exception.WrongPasswordException;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {
    boolean save(UserDto userDto) throws WrongPasswordException;
}
