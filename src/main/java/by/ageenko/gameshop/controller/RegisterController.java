package by.ageenko.gameshop.controller;

import by.ageenko.gameshop.exception.BucketServiceException;
import by.ageenko.gameshop.exception.UserServiceException;
import by.ageenko.gameshop.model.User;
import by.ageenko.gameshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
        return "register";
    }
    @PostMapping("/register")
    public String regSubmit(User user, Model model) throws UserServiceException, BucketServiceException {
        if (userService.saveFromRegisterForm(user)){
            return "redirect:/";
        }else {
            model.addAttribute("user", user);
            return "/register";
        }
    }
}
