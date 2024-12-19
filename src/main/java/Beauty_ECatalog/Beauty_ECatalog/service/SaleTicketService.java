package Beauty_ECatalog.Beauty_ECatalog.service;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.User;
import Beauty_ECatalog.Beauty_ECatalog.domain.Voucher;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqProduct;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqSaleTicket;
import Beauty_ECatalog.Beauty_ECatalog.repository.SaleTicketDetailRepository;
import Beauty_ECatalog.Beauty_ECatalog.repository.SaleTicketRepository;


@Service
public class SaleTicketService {
    private final SaleTicketRepository saleTicketRepository;
    private final SaleTicketDetailRepository saleTicketDetailRepository;
    private final UserService userService;
    private final ProductService productService;
    private final VoucherService voucherService;
    public SaleTicketService(SaleTicketRepository saleTicketRepository, SaleTicketDetailRepository saleTicketDetailRepository,UserService userService, ProductService productService, VoucherService voucherService){
        this.saleTicketRepository = saleTicketRepository;
        this.saleTicketDetailRepository = saleTicketDetailRepository;
        this.userService = userService;
        this.productService = productService;
        this.voucherService = voucherService;
    }

    public SaleTicket createSaleTicket(ReqSaleTicket reqSaleTicket){
        User user = this.userService.handleGetUserByUsername(reqSaleTicket.getEmail());
        Voucher voucher = this.voucherService.getDiscountById(reqSaleTicket.getDiscountId());
        SaleTicket saleTicket = new SaleTicket();
        saleTicket.setUser(user);
        saleTicket.setDiscount(voucher);
        saleTicket.setDate(reqSaleTicket.getDate());
        saleTicket.setTotal(reqSaleTicket.getTotal());
        saleTicket.setConfirm(false);
        saleTicket.setStatus(false);
        saleTicket = this.saleTicketRepository.save(saleTicket);
        for(ReqProduct product : reqSaleTicket.getListProducts()){
            SaleTicketDetail saleTicketDetail = new SaleTicketDetail();
            Product updateProduct = this.productService.getProductByName(product.getProductName());
            saleTicketDetail.setProduct(updateProduct);
            saleTicketDetail.setQuantity(product.getQuantity());
            saleTicketDetail.setSaleTicket(saleTicket);
            saleTicketDetail = this.saleTicketDetailRepository.save(saleTicketDetail);
        }
        return saleTicket;
    }
}
