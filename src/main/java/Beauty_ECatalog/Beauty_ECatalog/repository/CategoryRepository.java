package Beauty_ECatalog.Beauty_ECatalog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.Category;



@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    public Category findByName(String name);

    public boolean existsByName(String name);
}
