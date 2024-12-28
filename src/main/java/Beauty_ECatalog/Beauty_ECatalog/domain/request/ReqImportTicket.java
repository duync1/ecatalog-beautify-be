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
    private double totalPrice;
    private List<ProductInfor> listProducts;

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductInfor{
        private String productName;
        private double importPrice;
        private int quantity;
    }
}
