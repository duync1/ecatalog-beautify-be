package Beauty_ECatalog.Beauty_ECatalog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.ServiceTicketDetail;

@Repository
public interface ServiceTicketDetailRepository extends JpaRepository<ServiceTicketDetail, Long> {
    
}
