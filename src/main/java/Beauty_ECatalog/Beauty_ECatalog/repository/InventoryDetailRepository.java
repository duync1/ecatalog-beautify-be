package Beauty_ECatalog.Beauty_ECatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.Inventory;
import Beauty_ECatalog.Beauty_ECatalog.domain.InventoryDetail;
import java.util.List;


@Repository
public interface InventoryDetailRepository extends JpaRepository<InventoryDetail, Long> {
    List<InventoryDetail> findByInventory(Inventory inventory);
}

