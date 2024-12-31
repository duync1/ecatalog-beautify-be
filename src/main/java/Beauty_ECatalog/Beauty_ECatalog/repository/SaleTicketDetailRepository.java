    package Beauty_ECatalog.Beauty_ECatalog.repository;

    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;

    import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicket;
    import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicketDetail;

    import java.time.Instant;

    import java.util.List;


    @Repository
    public interface SaleTicketDetailRepository extends JpaRepository<SaleTicketDetail, Long>{

        @Query("SELECT SUM(std.quantity) FROM SaleTicketDetail std " +
            "JOIN SaleTicket st ON std.saleTicket.id = st.id " +
            "WHERE st.date BETWEEN :startDate AND :endDate AND std.product.id = :productId AND st.status = 'COMPLETED'")
        Integer findTotalSoldByProductAndDateRange(Long productId, Instant startDate, Instant endDate);
        
        List<SaleTicketDetail> findBySaleTicket(SaleTicket saleTicket);
    }
