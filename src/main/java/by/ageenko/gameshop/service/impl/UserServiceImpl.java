package by.ageenko.gameshop.service.impl;

import by.ageenko.gameshop.dto.UserDto;
import by.ageenko.gameshop.exception.WrongPasswordException;
import by.ageenko.gameshop.model.Role;
import by.ageenko.gameshop.model.User;
import by.ageenko.gameshop.repository.UserRepository;
import by.ageenko.gameshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean save(UserDto userDto) throws WrongPasswordException {
        if (!userDto.getPassword().equals(userDto.getMatchingPassword())){
            throw new WrongPasswordException("Password is not equals");
        }
        User user = User.builder()
                .name(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .role(Role.CLIENT)
                .build();
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByName(username);
        if (user == null){
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getName(),
                user.getPassword(),
                roles);
    }
}
