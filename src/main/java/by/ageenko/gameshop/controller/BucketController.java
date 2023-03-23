package by.ageenko.gameshop.controller;

import by.ageenko.gameshop.model.Bucket;
import by.ageenko.gameshop.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;

@Controller
public class BucketController {
    private final BucketService bucketService;
    @Autowired
    public BucketController(BucketService bucketService) {
        this.bucketService = bucketService;
    }
    @GetMapping("/bucket")
    public String bucketPage(Model model, Principal principal) throws UserPrincipalNotFoundException {
        if(principal == null){
            throw new UserPrincipalNotFoundException("You must be logged in");
        }
        else {
            Bucket bucket = bucketService.getBucketByUsername(principal.getName());
            model.addAttribute("bucket", bucket);
        }

        return "bucket";
    }
}
