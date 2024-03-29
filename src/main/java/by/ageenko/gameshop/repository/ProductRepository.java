package by.ageenko.gameshop.repository;

import by.ageenko.gameshop.model.Category;
import by.ageenko.gameshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoriesIn(Set<Category> categories);
}
