package Beauty_ECatalog.Beauty_ECatalog.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.Category;
import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
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
            if(this.checkExists(category.getName())){
                return null;
            }
            currentCategory.setName(category.getName());
            if(category.getProducts() != null){
                List<Long> productIds = new ArrayList<>();
                for (Product product : category.getProducts()) {
                    productIds.add(product.getId());
                }
                List<Product> newProducts = this.productRepository.findByIdIn(productIds);
                currentCategory.setProducts(newProducts);
            }
            return this.categoryRepository.save(currentCategory);
        }
        return null;
    }

    public void deleteCategory(long id){
        Category currentCategory = this.getCategoryById(id);
        currentCategory.setDeleted(true);
        List<Product> lists = this.productRepository.findByCategory(currentCategory);
        for(Product product : lists){
            product.setDeleted(true);
            this.productRepository.save(product);
        }
        this.categoryRepository.save(currentCategory);
    }

    public void dontDeleteCategory(long id){
        Category currentCategory = this.getCategoryById(id);
        currentCategory.setDeleted(false);
        List<Product> lists = this.productRepository.findByCategory(currentCategory);
        for(Product product : lists){
            product.setDeleted(false);
            this.productRepository.save(product);
        }
        this.categoryRepository.save(currentCategory);
    }

    public boolean checkExists(String name){
        boolean check = this.categoryRepository.existsByName(name);
        return check;
    }

    public ResultPaginationDTO fetchAllCategories(Specification<Category> spec, Pageable pageable) {
        Page<Category> page = this.categoryRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(page.getNumber() + 1);
        meta.setPageSize(page.getSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());
        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(page.getContent());
        return resultPaginationDTO;
    }
}
