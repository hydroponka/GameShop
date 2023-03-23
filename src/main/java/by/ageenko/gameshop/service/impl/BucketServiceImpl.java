package by.ageenko.gameshop.service.impl;

import by.ageenko.gameshop.exception.ServiceException;
import by.ageenko.gameshop.model.Bucket;
import by.ageenko.gameshop.model.Product;
import by.ageenko.gameshop.model.User;
import by.ageenko.gameshop.repository.BucketRepository;
import by.ageenko.gameshop.service.BucketService;
import by.ageenko.gameshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class BucketServiceImpl implements BucketService {
    private final BucketRepository bucketRepository;
    private final UserService userService;
    @Autowired
    public BucketServiceImpl(BucketRepository bucketRepository, UserService userService) {
        this.bucketRepository = bucketRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Bucket createBucket(User user) throws ServiceException {
        Bucket bucket = new Bucket();
        if (user!=null) {
            bucket.setProducts(new ArrayList<>());
            bucket.setUser(user);
        }else {
            throw new ServiceException("User not found");
        }
        return bucketRepository.save(bucket);
    }

    @Override
    @Transactional
    public void addProduct(Bucket bucket, Product product) throws ServiceException {
        Optional<Bucket> updatedBucket = bucketRepository.findById(bucket.getId());
        if (updatedBucket.isPresent()) {
            updatedBucket.get().getProducts().add(product);
            bucketRepository.save(updatedBucket.get());
        }else {
            throw new ServiceException("Bucket not found");
        }
    }

    @Override
    public Bucket getBucketByUsername(String username) {
        User user = userService.findByUsername(username);
        if(user == null || user.getBucket() == null){
            return new Bucket();
        }
        return user.getBucket();
    }
    @Override
    public int getBucketSize(String username){
        Bucket bucket = getBucketByUsername(username);
        if (bucket.getProducts()!=null){
            return bucket.getProducts().size();
        }else {
            return 0;
        }
    }

    @Override
    public void commitBucketToOrder(String username) {

    }
}
