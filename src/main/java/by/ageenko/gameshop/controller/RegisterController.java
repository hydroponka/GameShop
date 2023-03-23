package by.ageenko.gameshop.controller;

import by.ageenko.gameshop.exception.ServiceException;
import by.ageenko.gameshop.model.User;
import by.ageenko.gameshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

import static by.ageenko.gameshop.util.PageConst.*;

@Controller
public class RegisterController {
    private final UserService userService;
    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String regForm(Model model){
        model.addAttribute("user", new User());
        return auth_register;
    }
    @PostMapping("/register")
    public String regSubmit(User user, Model model, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        try {
            userService.registerUser(user, getSiteURL(request));
        }catch (ServiceException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "/register";
        }
        return auth_process_register;
    }

    private String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
