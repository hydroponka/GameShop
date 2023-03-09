package by.ageenko.gameshop.controller;

import by.ageenko.gameshop.dto.UserDto;
import by.ageenko.gameshop.exception.BucketServiceException;
import by.ageenko.gameshop.exception.UserServiceException;
import by.ageenko.gameshop.model.User;
import by.ageenko.gameshop.service.BucketService;
import by.ageenko.gameshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {
    private final UserService userService;
    private final BucketService bucketService;
    @Autowired
    public RegisterController(UserService userService, BucketService bucketService) {
        this.userService = userService;
        this.bucketService = bucketService;
    }

    @GetMapping("/register")
    public String regForm(Model model){
        model.addAttribute("user", new UserDto());
        return "register";
    }
    @PostMapping("/register")
    public String regSubmit(UserDto userDto, Model model) throws UserServiceException, BucketServiceException {
        if (userService.save(userDto)){
            return "redirect:/";
        }else {
            model.addAttribute("user", userDto);
            return "/register";
        }
    }
}
