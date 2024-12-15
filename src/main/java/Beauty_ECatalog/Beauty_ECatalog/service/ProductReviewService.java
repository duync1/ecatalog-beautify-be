package Beauty_ECatalog.Beauty_ECatalog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import Beauty_ECatalog.Beauty_ECatalog.domain.ProductReview;

import Beauty_ECatalog.Beauty_ECatalog.domain.User;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqProductReview;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;

import Beauty_ECatalog.Beauty_ECatalog.repository.ProductReviewRepository;

@Service
public class ProductReviewService {
    private final ProductReviewRepository productReviewRepository;
    
    private final ProductService productService;
    private final UserService userService;
    public ProductReviewService(ProductReviewRepository productReviewRepository, ProductService productService, UserService userService){
        this.productReviewRepository = productReviewRepository;
        this.productService = productService;
        this.userService = userService;
    }

    public ProductReview createComment(ReqProductReview reqProductReview){
        Product product = this.productService.getProductById(reqProductReview.getProductId());
        User user = this.userService.handleGetUserByUsername(reqProductReview.getEmail());
        ProductReview productReview = new ProductReview();
        productReview.setDate(reqProductReview.getDate());
        productReview.setDetailReview(reqProductReview.getDetailReview());
        productReview.setProduct(product);
        productReview.setRating(reqProductReview.getRating());
        productReview.setReviewSummary(reqProductReview.getReviewSummary());
        productReview.setUser(user);
        return this.productReviewRepository.save(productReview);
    }

    public ResultPaginationDTO fetchAllProductReviews(Specification<ProductReview> spec, Pageable pageable) {
        Page<ProductReview> page = this.productReviewRepository.findAll(spec, pageable);
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

    public void handleDeleteComment(long id){
        this.productReviewRepository.deleteById(id);
    }
}
