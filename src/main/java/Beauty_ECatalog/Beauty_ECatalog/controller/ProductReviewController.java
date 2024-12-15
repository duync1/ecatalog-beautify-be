package Beauty_ECatalog.Beauty_ECatalog.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import Beauty_ECatalog.Beauty_ECatalog.domain.ProductReview;

import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqProductReview;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.ProductReviewService;

@RestController
public class ProductReviewController {
    private final ProductReviewService productReviewService;
    public ProductReviewController(ProductReviewService productReviewService){
        this.productReviewService = productReviewService;
    }

    @PostMapping("/ProductReview/CreateComment")
    public ResponseEntity<ProductReview> createComment(@RequestBody ReqProductReview productReview){
        ProductReview saveProductReview = this.productReviewService.createComment(productReview);
        return ResponseEntity.ok().body(saveProductReview);
    }

    @GetMapping("/ProductReviews")
    public ResponseEntity<ResultPaginationDTO> getAllProductReview(@Filter Specification<ProductReview> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.productReviewService.fetchAllProductReviews(spec, pageable));
    }

    @DeleteMapping("/ProductReviews/{id}")
    public ResponseEntity<Void> deleteProductReview(@PathVariable("id") long id){
        this.productReviewService.handleDeleteComment(id);
        return ResponseEntity.ok().body(null);
    }
}
