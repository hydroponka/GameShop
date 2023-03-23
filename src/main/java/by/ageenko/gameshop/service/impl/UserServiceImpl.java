package by.ageenko.gameshop.service.impl;

import by.ageenko.gameshop.exception.ServiceException;
import by.ageenko.gameshop.model.Role;
import by.ageenko.gameshop.model.User;
import by.ageenko.gameshop.repository.UserRepository;
import by.ageenko.gameshop.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.ageenko.gameshop.util.EmailConst.*;

@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    public void registerUser(User user, String siteUrl) throws ServiceException, MessagingException, UnsupportedEncodingException {
        if (userRepository.findFirstByUsername(user.getUsername()).isPresent()) {
            throw new ServiceException("This username is already registered.");
        }
        if (!user.getPassword().equals(user.getMatchingPassword())) {
            throw new ServiceException("Password is not equals");
        }
        String randomCode = RandomString.make(64);
        User newUser = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .email(user.getEmail())
                .verificationCode(randomCode)
                .enabled(false)
                .role(Role.CLIENT)
                .build();
        save(newUser);
        sendVerificationEmail(newUser, siteUrl);
    }

    private void sendVerificationEmail(User user, String url) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(companyEmail, companyName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        String contentName = content.replace("[[name]]", user.getUsername());
        String verifyURL = url + "/verify?code=" + user.getVerificationCode();

        contentName = contentName.replace("[[URL]]", verifyURL);

        helper.setText(contentName, true);

        mailSender.send(message);
    }
    @Override
    public boolean verify(String code) {
        Optional<User> user = userRepository.findUserByVerificationCode(code);

        if (user.isEmpty() || user.get().isEnabled()) {
            return false;
        } else {
            user.get().setVerificationCode(null);
            user.get().setEnabled(true);
            userRepository.save(user.get());

            return true;
        }
    }
    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                roles);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
    }
}
