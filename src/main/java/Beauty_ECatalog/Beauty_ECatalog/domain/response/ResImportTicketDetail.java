package Beauty_ECatalog.Beauty_ECatalog.domain.response;


import java.time.Instant;
import java.util.List;

import Beauty_ECatalog.Beauty_ECatalog.domain.ImportTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResImportTicketDetail {
    private String supplier;
    private Instant date;
    private long total;
    private boolean status;
    private List<ProductInTicket> listProducts;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ProductInTicket{
        private Product product;
        private int quantity;
    }
}
