package Beauty_ECatalog.Beauty_ECatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicketDetail;
import java.util.List;


@Repository
public interface SaleTicketDetailRepository extends JpaRepository<SaleTicketDetail, Long>{
    List<SaleTicketDetail> findBySaleTicket(SaleTicket saleTicket);
}
