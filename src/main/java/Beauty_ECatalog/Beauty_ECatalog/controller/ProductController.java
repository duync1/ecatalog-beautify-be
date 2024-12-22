package Beauty_ECatalog.Beauty_ECatalog.controller;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.turkraft.springfilter.boot.Filter;

import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.ProductService;
import Beauty_ECatalog.Beauty_ECatalog.util.error.IdInvalidException;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/Product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) throws IdInvalidException{
        Product product = this.productService.getProductById(id);
        if(product == null){
            throw new IdInvalidException("Product not found");
        }
        return ResponseEntity.ok().body(product);
    }

    @PostMapping("/Product")
    public ResponseEntity<Product> createProduct(@RequestParam("name") String name, @RequestParam("unitPrice") int price, 
        @RequestParam("image") MultipartFile image, @RequestParam("brand") String brand, @RequestParam("category") String categoryName, @RequestParam("detailDescription") String description
    ) throws IOException, IdInvalidException{
        if(this.productService.findProductByName(name)){
            throw new IdInvalidException("Ten san pham da ton tai");
        }
        Product product = new Product();
        product.setName(name);
        product.setUnitPrice(price);
        product.setBrand(brand);
        product.setDetailDescription(description);
        product.setDeleted(false);
        return ResponseEntity.ok().body(this.productService.createProduct(product, image, categoryName));
    }

    @GetMapping("/Product")
    public ResponseEntity<ResultPaginationDTO> getAllProduct(@Filter Specification<Product> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.productService.fetchAllProducts(spec, pageable));
    }

    @DeleteMapping("/Product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") long id){
        this.productService.deleteProduct(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/Product/Back/{id}")
    public ResponseEntity<Void> backProduct(@PathVariable("id") long id){
        this.productService.backProduct(id);
        return ResponseEntity.ok().body(null);
    }
    
    @PutMapping("/Product")
    public ResponseEntity<Product> updateProduct(@RequestParam("id") long id, @RequestParam("name") String name, @RequestParam("unitPrice") int price, @RequestParam(value = "image", required = false) MultipartFile image, @RequestParam("brand") String brand, @RequestParam("category") String categoryName, @RequestParam("detailDescription") String description) throws IdInvalidException, IOException{
        Product temp = this.productService.getProductById(id);
        if(!temp.getName().equals(name)){
            boolean check = this.productService.findProductByName(name);
            if(check){
                throw new IdInvalidException("Ten san pham da ton tai");
            }
        }
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setUnitPrice(price);
        product.setBrand(brand);
        product.setDetailDescription(description);
        Product updateProduct = this.productService.updateProduct(product, image, categoryName);
        if(updateProduct == null){
            throw new IdInvalidException("San pham khong ton tai");
        }
        return ResponseEntity.ok().body(updateProduct);
    }
    
}
