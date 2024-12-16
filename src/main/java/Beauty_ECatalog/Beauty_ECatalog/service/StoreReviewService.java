package Beauty_ECatalog.Beauty_ECatalog.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.ProductReview;
import Beauty_ECatalog.Beauty_ECatalog.domain.StoreReview;
import Beauty_ECatalog.Beauty_ECatalog.domain.User;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqStoreReview;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.repository.StoreReviewRepository;


@Service
public class StoreReviewService {
    private final StoreReviewRepository storeReviewRepository;
    private final UserService userService;
    public StoreReviewService(StoreReviewRepository storeReviewRepository, UserService userService){
        this.storeReviewRepository = storeReviewRepository;
        this.userService = userService;
    }

    public StoreReview createComment(ReqStoreReview reqStoreReview){
        User user = this.userService.handleGetUserByUsername(reqStoreReview.getEmail());
        StoreReview storeReview = new StoreReview();
        storeReview.setComment(reqStoreReview.getComment());
        storeReview.setDeliveryQuality(reqStoreReview.getDeliveryQuality());
        storeReview.setProductQuality(reqStoreReview.getProductQuality());
        storeReview.setServiceQuality(reqStoreReview.getServiceQuality());
        storeReview.setStatus(false);
        storeReview.setTitle(reqStoreReview.getTitle());
        storeReview.setUser(user);
        return this.storeReviewRepository.save(storeReview);
    }

    public ResultPaginationDTO fetchAllStoreReviews(Specification<StoreReview> spec, Pageable pageable) {
        Page<StoreReview> page = this.storeReviewRepository.findAll(spec, pageable);
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
        this.storeReviewRepository.deleteById(id);
    }

    public StoreReview getCommentById(long id){
        Optional<StoreReview> storeReview = this.storeReviewRepository.findById(id);
        if(storeReview.isPresent()){
            return storeReview.get();
        }
        return null;
    }
    public StoreReview handleUpdateComment(StoreReview storeReview){
        StoreReview dbStoreReview = this.getCommentById(storeReview.getId());
        dbStoreReview.setComment(storeReview.getComment());
        dbStoreReview.setDeliveryQuality(storeReview.getDeliveryQuality());
        dbStoreReview.setProductQuality(storeReview.getProductQuality());
        dbStoreReview.setServiceQuality(storeReview.getServiceQuality());
        dbStoreReview.setTitle(storeReview.getTitle());
        return this.storeReviewRepository.save(dbStoreReview);
    }
}
