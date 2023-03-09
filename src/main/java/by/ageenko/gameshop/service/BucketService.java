package by.ageenko.gameshop.service;

import by.ageenko.gameshop.exception.BucketServiceException;
import by.ageenko.gameshop.model.Bucket;
import by.ageenko.gameshop.model.Product;
import by.ageenko.gameshop.model.User;

public interface BucketService {
    Bucket createBucket(User user) throws BucketServiceException;

    void addProduct(Bucket bucket, Product product);

    Bucket getBucketByUser(String name);

    void commitBucketToOrder(String username);
}
