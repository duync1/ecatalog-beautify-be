package Beauty_ECatalog.Beauty_ECatalog.domain.response;

import java.util.List;

import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResSaleTicketDetail {
    private SaleTicket saleTicket;
    private List<ProductInTicket> listProducts;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductInTicket{
        private Product product;
        private int quantity;
    }
}
