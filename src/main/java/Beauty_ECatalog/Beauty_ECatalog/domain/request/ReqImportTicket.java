package Beauty_ECatalog.Beauty_ECatalog.domain.request;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReqImportTicket {
    private String supplierName;
    private Instant date;
    private int totalPrice;
    private List<ProductInfor> listProducts;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductInfor{
        private String productName;
        private int quantity;
    }
}
