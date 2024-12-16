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

import Beauty_ECatalog.Beauty_ECatalog.domain.StoreReview;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqStoreReview;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.StoreReviewService;

@RestController
public class StoreReviewController {
    private final StoreReviewService storeReviewService;
    public StoreReviewController(StoreReviewService storeReviewService){
        this.storeReviewService = storeReviewService;
    }

    @PostMapping("/StoreReviews")
    public ResponseEntity<StoreReview> createStoreReview(@RequestBody ReqStoreReview reqStoreReview){
        StoreReview storeReview = this.storeReviewService.createComment(reqStoreReview);
        return ResponseEntity.ok().body(storeReview);
    }

    @GetMapping("/StoreReviews")
    public ResponseEntity<ResultPaginationDTO> getAllComment(@Filter Specification<StoreReview> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.storeReviewService.fetchAllStoreReviews(spec, pageable));
    }

    @DeleteMapping("/StoreReviews/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("id") long id){
        this.storeReviewService.handleDeleteComment(id);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/StoreReviews")
    public ResponseEntity<StoreReview> updateComment(@RequestBody StoreReview storeReview){
        return ResponseEntity.ok().body(this.storeReviewService.handleUpdateComment(storeReview));
    }
}
