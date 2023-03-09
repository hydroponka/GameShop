package by.ageenko.gameshop.controller;

import by.ageenko.gameshop.exception.BucketServiceException;
import by.ageenko.gameshop.model.Product;
import by.ageenko.gameshop.service.BucketService;
import by.ageenko.gameshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class MainController {
    private final ProductService productService;
    @Autowired
    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping({"", "/"})
    public String index(Model model){
        model.addAttribute("products", productService.getAll());
        return "index";
    }
    @PostMapping("/{id}/bucket")
    public String addBucket(@PathVariable Long id, Principal principal) throws BucketServiceException {
        if(principal == null){
            return "redirect:/";
        }
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/";
    }
}
