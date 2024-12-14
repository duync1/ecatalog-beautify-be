package Beauty_ECatalog.Beauty_ECatalog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.ImportTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.ImportTicketDetail;

@Repository
public interface ImportTicketDetailRepository extends JpaRepository<ImportTicketDetail, Long>{
    public List<ImportTicketDetail> findByImportTicket(ImportTicket importTicket);
}
