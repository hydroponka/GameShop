package by.ageenko.gameshop.controller;

import by.ageenko.gameshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import static by.ageenko.gameshop.util.PageConst.*;

@Controller
@RequestMapping("/verify")
public class VerifyController {
    private final UserService userService;
    @Autowired
    public VerifyController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String verifyUser(@RequestParam("code") String code) {
        if (userService.verify(code)) {
            return auth_verify_success;
        } else {
            return auth_verify_fail;
        }
    }
}
