package Beauty_ECatalog.Beauty_ECatalog.repository;

import java.time.Instant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Beauty_ECatalog.Beauty_ECatalog.domain.ImportTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.ImportTicketDetail;

@Repository
public interface ImportTicketDetailRepository extends JpaRepository<ImportTicketDetail, Long>{
    @Query("SELECT SUM(itd.quantity) FROM ImportTicketDetail itd " +
           "JOIN ImportTicket it ON itd.importTicket.id = it.id " +
           "WHERE it.date BETWEEN :startDate AND :endDate AND itd.product.id = :productId")
    Integer findTotalImportedByProductAndDateRange(Long productId, Instant startDate, Instant endDate);

    public List<ImportTicketDetail> findByImportTicket(ImportTicket importTicket);
}
