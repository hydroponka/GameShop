package by.ageenko.gameshop.service;

import by.ageenko.gameshop.exception.ServiceException;
import by.ageenko.gameshop.model.Bucket;
import by.ageenko.gameshop.model.Product;
import by.ageenko.gameshop.model.User;

public interface BucketService {
    Bucket createBucket(User user) throws ServiceException;

    void addProduct(Bucket bucket, Product product) throws ServiceException;

    Bucket getBucketByUsername(String name);

    int getBucketSize(String username);

    void commitBucketToOrder(String username);
}
