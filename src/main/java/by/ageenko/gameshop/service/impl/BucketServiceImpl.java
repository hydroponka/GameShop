package by.ageenko.gameshop.service.impl;

import by.ageenko.gameshop.exception.BucketServiceException;
import by.ageenko.gameshop.model.Bucket;
import by.ageenko.gameshop.model.Product;
import by.ageenko.gameshop.model.User;
import by.ageenko.gameshop.repository.BucketRepository;
import by.ageenko.gameshop.service.BucketService;
import by.ageenko.gameshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Bucket createBucket(User user) throws BucketServiceException {
        Bucket bucket = new Bucket();
        if (user!=null) {
            bucket.setUser(user);
        }else throw new BucketServiceException();
        return bucketRepository.save(bucket);
    }

    @Override
    @Transactional
    public void addProduct(Bucket bucket, Product product) {
        Optional<Bucket> updatedBucket = bucketRepository.findById(bucket.getId());
        updatedBucket.get().getProducts().add(product);
        bucketRepository.save(updatedBucket.get());
    }

    @Override
    public Bucket getBucketByUser(String name) {
        User user = userService.findByName(name);
        if(user == null || user.getBucket() == null){
            return new Bucket();
        }
        return user.getBucket();
    }

    @Override
    public void commitBucketToOrder(String username) {

    }
}
