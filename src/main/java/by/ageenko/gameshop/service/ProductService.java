package by.ageenko.gameshop.service;


import by.ageenko.gameshop.exception.ServiceException;
import by.ageenko.gameshop.model.Category;
import by.ageenko.gameshop.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {
    List<Product> findAllProducts();
    List<Category> findAllCategories();
    boolean addToUserBucket(Long productId, String username) throws ServiceException;
    void addProduct(Product product);
    void addCategory(String title);

    List<Product> findByCategoryTitle(Set<String> categories);
    Product findById(Long id) throws ServiceException;
    void deleteProduct(Long id);

    List<Category> findCategoriesById(Set<Long> categoryIds);
}
