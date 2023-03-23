package by.ageenko.gameshop.service.impl;

import by.ageenko.gameshop.exception.ServiceException;
import by.ageenko.gameshop.model.Bucket;
import by.ageenko.gameshop.model.Category;
import by.ageenko.gameshop.model.Product;
import by.ageenko.gameshop.model.User;
import by.ageenko.gameshop.repository.CategoryRepository;
import by.ageenko.gameshop.repository.ProductRepository;
import by.ageenko.gameshop.service.BucketService;
import by.ageenko.gameshop.service.ProductService;
import by.ageenko.gameshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final BucketService bucketService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserService userService, BucketService bucketService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.bucketService = bucketService;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public boolean addToUserBucket(Long productId, String username) throws ServiceException {
        User user = userService.findByUsername(username);
        if (!user.isEnabled()){
            return false;
        }
        Bucket bucket = user.getBucket();
        Product product = findById(productId);
        if(bucket == null){
            Bucket newBucket = bucketService.createBucket(user);
            user.setBucket(newBucket);
            bucketService.addProduct(user.getBucket(), product);
            userService.save(user);
        }
        else {
            bucketService.addProduct(bucket, product);
        }
        return true;
    }

    @Override
    public void addProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void addCategory(String title) {
        categoryRepository.save(Category.builder()
                .title(title)
                .build());
    }
    @Override
    public List<Product> findByCategoryTitle(Set<String> categoriesTitle) {
        List<Category> categories = categoryRepository.findByTitleIn(categoriesTitle);
        return productRepository.findByCategoriesIn(new HashSet<>(categories));
    }

    @Override
    public Product findById(Long id) throws ServiceException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }else {
            throw new ServiceException("Product with id = " + id + "not found");
        }
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Category> findCategoriesById(Set<Long> categoryIds) {
        List<Category> categories = categoryRepository.findAllById(categoryIds);
        for (Category category : categories) {
            category.setProducts(null);
        }
        return categories;
    }
}
