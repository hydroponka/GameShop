package by.ageenko.gameshop.repository;

import by.ageenko.gameshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByTitleIn(Set<String> title);
}
