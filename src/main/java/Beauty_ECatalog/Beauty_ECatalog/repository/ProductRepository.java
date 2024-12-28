package Beauty_ECatalog.Beauty_ECatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.Category;
import Beauty_ECatalog.Beauty_ECatalog.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product>{
    List<Product> findByIdIn(List<Long> id);
    public List<Product> findByCategory(Category category);
    public boolean existsByName(String name);
    public Product findByName(String name);
    public List<Product> findAll();
}
