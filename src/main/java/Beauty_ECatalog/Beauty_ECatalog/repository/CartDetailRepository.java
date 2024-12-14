package Beauty_ECatalog.Beauty_ECatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.Cart;
import Beauty_ECatalog.Beauty_ECatalog.domain.CartDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {
    public CartDetail findByCartAndProduct(Cart cart, Product product);
}
