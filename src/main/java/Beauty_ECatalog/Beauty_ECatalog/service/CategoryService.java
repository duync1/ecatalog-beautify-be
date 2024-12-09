package Beauty_ECatalog.Beauty_ECatalog.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.Category;
import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import Beauty_ECatalog.Beauty_ECatalog.repository.CategoryRepository;
import Beauty_ECatalog.Beauty_ECatalog.repository.ProductRepository;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository){
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public Category getCategoryById(long id){
        Optional<Category> category = this.categoryRepository.findById(id);
        if(category.isPresent()){
            return category.get();
        }
        return null;
    }

    public Category createCategory(Category category){
        return this.categoryRepository.save(category);
    }

    public Category updateCategory(Category category){
        Category currentCategory = this.getCategoryById(category.getId());
        if(currentCategory != null){
            currentCategory.setName(category.getName());
            if(category.getProducts() != null){
                List<Long> productIds = new ArrayList<>();
                for (Product product : category.getProducts()) {
                    productIds.add(product.getId());
                }
                List<Product> newProducts = this.productRepository.findByIdIn(productIds);
                currentCategory.setProducts(newProducts);
            }
            return currentCategory;
        }
        return null;
    }

    public void deleteCategory(long id){
        Category currentCategory = this.getCategoryById(id);
        if(currentCategory != null){
            List<Product> list = this.productRepository.findByCategory(currentCategory);
            this.productRepository.deleteAll(list);
            this.categoryRepository.delete(currentCategory);
        }
    }
}
