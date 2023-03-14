package by.ageenko.gameshop.service.impl;

import by.ageenko.gameshop.exception.BucketServiceException;
import by.ageenko.gameshop.exception.UserServiceException;
import by.ageenko.gameshop.model.Bucket;
import by.ageenko.gameshop.model.Product;
import by.ageenko.gameshop.model.User;
import by.ageenko.gameshop.repository.ProductRepository;
import by.ageenko.gameshop.service.BucketService;
import by.ageenko.gameshop.service.ProductService;
import by.ageenko.gameshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final UserService userService;
    private final BucketService bucketService;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public void addToUserBucket(Long productId, String username) throws BucketServiceException, UserServiceException {
        User user = userService.findByName(username);
        if (user == null){
            throw new UsernameNotFoundException("User" + username + "not found");
        }
        Bucket bucket = user.getBucket();
        Product product = productRepository.getOne(productId);
        if(bucket == null){
            Bucket newBucket = bucketService.createBucket(user);
            user.setBucket(newBucket);
            userService.save(user);
        }
        else {
            bucketService.addProduct(bucket, product);
        }
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElse(new Product());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
