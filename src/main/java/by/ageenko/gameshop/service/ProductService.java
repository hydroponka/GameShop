package by.ageenko.gameshop.service;


import by.ageenko.gameshop.exception.BucketServiceException;
import by.ageenko.gameshop.exception.UserServiceException;
import by.ageenko.gameshop.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    void addToUserBucket(Long productId, String username) throws BucketServiceException, UserServiceException;
    void addProduct(Product product);
    Product getById(Long id);
    void deleteProduct(Long id);
}
