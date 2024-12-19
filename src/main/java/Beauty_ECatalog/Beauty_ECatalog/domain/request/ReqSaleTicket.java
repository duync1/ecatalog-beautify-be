package Beauty_ECatalog.Beauty_ECatalog.domain.request;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReqSaleTicket {
    private String email;
    private Instant date;
    private long total;
    private long discountId;
    private List<ReqProduct> listProducts;
}
