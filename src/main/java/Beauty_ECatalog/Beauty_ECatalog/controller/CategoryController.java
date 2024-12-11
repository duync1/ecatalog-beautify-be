package Beauty_ECatalog.Beauty_ECatalog.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import Beauty_ECatalog.Beauty_ECatalog.domain.Category;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.CategoryService;
import Beauty_ECatalog.Beauty_ECatalog.util.error.IdInvalidException;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }
    @GetMapping("/Category/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") long id) throws IdInvalidException{
        Category category = this.categoryService.getCategoryById(id);
        if(category == null){
            throw new IdInvalidException("Category not found");
        }
        return ResponseEntity.ok().body(category);
    }

    @PostMapping("/Category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) throws IdInvalidException{
        boolean check = this.categoryService.checkExists(category.getName());
        if(check){
            throw new IdInvalidException("Category da ton tai");
        }
        return ResponseEntity.ok().body(this.categoryService.createCategory(category));
    }

    @PutMapping("/Category")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) throws IdInvalidException{
        Category currentCategory = this.categoryService.updateCategory(category);
        if(currentCategory == null){
            throw new IdInvalidException("Category not found");
        }
        return ResponseEntity.ok().body(currentCategory);
    }

    @DeleteMapping("/Category/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("id") long id){
        this.categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/Category")
    public ResponseEntity<ResultPaginationDTO> getAllCategories(@Filter Specification<Category> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.categoryService.fetchAllCategories(spec, pageable));
    }

    @GetMapping("/Category/Back/{id}")
    public ResponseEntity<Void> backCategory(@PathVariable("id") long id){
        this.categoryService.dontDeleteCategory(id);
        return ResponseEntity.ok().body(null);
    }
}
