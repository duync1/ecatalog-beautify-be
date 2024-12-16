package Beauty_ECatalog.Beauty_ECatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.StoreReview;

@Repository
public interface StoreReviewRepository extends JpaRepository<StoreReview, Long>, JpaSpecificationExecutor<StoreReview>{
    
}
