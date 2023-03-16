package by.ageenko.gameshop.controller;

import by.ageenko.gameshop.exception.BucketServiceException;
import by.ageenko.gameshop.exception.UserServiceException;
import by.ageenko.gameshop.service.BucketService;
import by.ageenko.gameshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;

@Controller
public class MainController {
    private final ProductService productService;
    private final BucketService bucketService;
    @Autowired
    public MainController(ProductService productService, BucketService bucketService) {
        this.productService = productService;
        this.bucketService = bucketService;
    }

    @RequestMapping({"", "/"})
    public String index(Model model, Principal principal){
        model.addAttribute("products", productService.getAll());
        if (principal != null) {
            model.addAttribute("productCount", bucketService.getBucketByUser(principal.getName()).getProducts().size());
        }
        return "index";
    }

    @PostMapping("/{id}/bucket")
    public String addProductToBucket(@PathVariable Long id, Principal principal) throws BucketServiceException, UserServiceException{
        if(principal == null){
            return "redirect:/";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/";
    }
}
