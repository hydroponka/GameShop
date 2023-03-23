package by.ageenko.gameshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static by.ageenko.gameshop.util.PageConst.auth_login;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return auth_login;
    }
}
