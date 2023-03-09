package by.ageenko.gameshop.repository;

import by.ageenko.gameshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
