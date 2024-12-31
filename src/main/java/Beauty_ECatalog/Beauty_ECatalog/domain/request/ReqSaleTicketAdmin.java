package Beauty_ECatalog.Beauty_ECatalog.domain.request;

import java.time.Instant;
import java.util.List;

import Beauty_ECatalog.Beauty_ECatalog.domain.Voucher;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class ReqSaleTicketAdmin {
    private final String cusName;
    private final String phoneNumber;
    private final String address;
    private final Instant date;
    private double totalPrice;
    private long voucherId;
    List<ReqProduct> listProducts;
}
