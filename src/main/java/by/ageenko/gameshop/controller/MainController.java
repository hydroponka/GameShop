package by.ageenko.gameshop.controller;

import by.ageenko.gameshop.exception.ServiceException;
import by.ageenko.gameshop.service.BucketService;
import by.ageenko.gameshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static by.ageenko.gameshop.util.PageConst.home;

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
        model.addAttribute("products", productService.findAllProducts());
        model.addAttribute("categories", productService.findAllCategories());
        if (principal != null) {
            model.addAttribute("productCount", bucketService.getBucketSize(principal.getName()));
        }
        return home;
    }

    @PostMapping("/{id}/bucket")
    public ResponseEntity<String> addProductToBucket(@PathVariable Long id, Principal principal) throws ServiceException {
        if (principal == null) {
            return ResponseEntity.badRequest().body("You need to be authorized");
        }
        if (productService.addToUserBucket(id, principal.getName())) {
            return ResponseEntity.ok("Product added successfully");
        }else {
            return ResponseEntity.badRequest().body("You need to verify your account");
        }
    }
    @PostMapping("/categories")
    public String categoryForm(@PathVariable Long id, Model model) {
        return "redirect:" + home;
    }

}
