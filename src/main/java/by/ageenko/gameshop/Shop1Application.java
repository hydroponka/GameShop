package by.ageenko.gameshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Shop1Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Shop1Application.class, args);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        System.out.println(passwordEncoder.encode("Cergey"));
    }

}
