package Beauty_ECatalog.Beauty_ECatalog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Beauty_ECatalog.Beauty_ECatalog.domain.Category;
import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.repository.CategoryRepository;
import Beauty_ECatalog.Beauty_ECatalog.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final String uploadDir = "src/main/resources/static/uploads/";
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product getProductById(long id){
        Optional<Product> product = this.productRepository.findById(id);
        if(product.isPresent()){
            return product.get();
        }
        return null;
    }

    public Product createProduct(Product product, MultipartFile multipartFile, String categoryName) throws IOException{
        String imagePath = saveImage(multipartFile);
        Category category = this.categoryRepository.findByName(categoryName);
        product.setProductImage(imagePath);
        product.setCategory(category);
        return this.productRepository.save(product);
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file is empty");
        }
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
        Path filePath = path.resolve(fileName);
        Files.write(filePath, imageFile.getBytes());

        return "/uploads/" + fileName;
    }

    public ResultPaginationDTO fetchAllProducts(Specification<Product> spec, Pageable pageable) {
        Page<Product> page = this.productRepository.findAll(spec, pageable);
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

    public boolean findProductByName(String name){
        boolean product = this.productRepository.existsByName(name);
        if(product){
            return true;
        }
        return false;
    }

    public void deleteProduct(long id){
        this.productRepository.deleteById(id);
    }

    // public Product updateProduct(Product product){

    // }
}
