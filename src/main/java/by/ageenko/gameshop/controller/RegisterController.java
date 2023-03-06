package by.ageenko.gameshop.controller;

import by.ageenko.gameshop.dto.UserDto;
import by.ageenko.gameshop.exception.WrongPasswordException;
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
        model.addAttribute("user", new UserDto());
        return "register";
    }
    @PostMapping("/register")
    public String regSubmit(UserDto userDto, Model model) throws WrongPasswordException {
        if (userService.save(userDto)){
            return "redirect:/";
        }else {
            model.addAttribute("user", userDto);
            return "/register";
        }
    }
}
