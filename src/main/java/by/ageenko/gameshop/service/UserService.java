package by.ageenko.gameshop.service;

import by.ageenko.gameshop.exception.ServiceException;
import by.ageenko.gameshop.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;


public interface UserService extends UserDetailsService {
    void registerUser(User user, String siteUrl) throws ServiceException, MessagingException, UnsupportedEncodingException;
    void save(User user) throws ServiceException;
    List<User> findAll();
    User findByUsername(String name);

    boolean verify(String code);


}
