package Beauty_ECatalog.Beauty_ECatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.ServiceTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.ServiceTicketDetail;

@Repository
public interface ServiceTicketDetailRepository extends JpaRepository<ServiceTicketDetail, Long> {
    public List<ServiceTicketDetail> findByServiceTicket(ServiceTicket serviceTicket);
}
