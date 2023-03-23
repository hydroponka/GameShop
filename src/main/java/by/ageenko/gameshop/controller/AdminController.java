package by.ageenko.gameshop.controller;

import by.ageenko.gameshop.model.Category;
import by.ageenko.gameshop.model.Product;
import by.ageenko.gameshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static by.ageenko.gameshop.util.PageConst.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService productService;
    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping()
    public String adminPage(){
        return adminPanel;
    }
    @GetMapping("/products")
    public String addForm(Model model){
        model.addAttribute("categories", productService.findAllCategories());
        model.addAttribute("products", productService.findAllProducts());
        return admin_products;
    }
    @PostMapping("/products/addProduct")
    public String addProduct(@RequestParam("title") String title, @RequestParam("price") BigDecimal price,
                             @RequestParam(name = "categoryIds", required = false) Set<Long> categoryIds){
        Set<Category> categories = new HashSet<>(productService.findCategoriesById(categoryIds));
        productService.addProduct(Product.builder()
                        .title(title)
                        .price(price)
                        .categories(categories)
                        .build());
        return "redirect:" + admin_products;
    }

    @GetMapping("/categories")
    public String addCategories(Model model){
        model.addAttribute("categories", productService.findAllCategories());
        return admin_categories;
    }

    @PostMapping("/categories/addCategories")
    public String addProduct(@RequestParam("title") String title){
        productService.addCategory(title);
        return "redirect:" + admin_categories;
    }
}
