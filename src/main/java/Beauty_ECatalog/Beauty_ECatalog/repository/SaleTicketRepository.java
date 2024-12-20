package Beauty_ECatalog.Beauty_ECatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.User;

import java.util.List;


@Repository
public interface SaleTicketRepository extends JpaRepository<SaleTicket, Long>, JpaSpecificationExecutor<SaleTicket>{
    List<SaleTicket> findByUser(User user);
    
}
