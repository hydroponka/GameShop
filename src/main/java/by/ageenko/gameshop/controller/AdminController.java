package by.ageenko.gameshop.controller;

import by.ageenko.gameshop.model.Product;
import by.ageenko.gameshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Controller
public class AdminController {

    private final ProductService productService;
    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model){
        model.addAttribute("products", productService.getAll());
        return "/admin";
    }
    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/addProduct")
    public String addProduct(@RequestParam("title") String title, @RequestParam("price") BigDecimal price){
        productService.addProduct(Product.builder()
                        .title(title)
                        .price(price)
                        .build());
        return "redirect:/admin";
    }
}
