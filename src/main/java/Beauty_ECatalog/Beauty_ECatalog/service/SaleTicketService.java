package Beauty_ECatalog.Beauty_ECatalog.service;

import java.io.ObjectInputFilter.Status;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicketDetail;

import Beauty_ECatalog.Beauty_ECatalog.domain.User;
import Beauty_ECatalog.Beauty_ECatalog.domain.Voucher;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqProduct;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqSaleTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResSaleTicketDetail.ProductInTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResSaleTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.repository.ProductRepository;
import Beauty_ECatalog.Beauty_ECatalog.repository.SaleTicketDetailRepository;
import Beauty_ECatalog.Beauty_ECatalog.repository.SaleTicketRepository;
import Beauty_ECatalog.Beauty_ECatalog.util.constant.StatusEnum;


@Service
public class SaleTicketService {
    private final SaleTicketRepository saleTicketRepository;
    private final SaleTicketDetailRepository saleTicketDetailRepository;
    private final UserService userService;
    private final ProductService productService;
    private final VoucherService voucherService;
    private final ProductRepository productRepository;
    public SaleTicketService(SaleTicketRepository saleTicketRepository, SaleTicketDetailRepository saleTicketDetailRepository,UserService userService, ProductService productService, VoucherService voucherService, ProductRepository productRepository){
        this.saleTicketRepository = saleTicketRepository;
        this.saleTicketDetailRepository = saleTicketDetailRepository;
        this.userService = userService;
        this.productService = productService;
        this.voucherService = voucherService;
        this.productRepository = productRepository;
    }

    public SaleTicket createSaleTicket(ReqSaleTicket reqSaleTicket){
        User user = this.userService.handleGetUserByUsername(reqSaleTicket.getEmail());
        Voucher voucher = this.voucherService.getDiscountById(reqSaleTicket.getDiscountId());
        
        SaleTicket saleTicket = new SaleTicket();
        saleTicket.setUser(user);
        saleTicket.setDiscount(voucher);
        saleTicket.setDate(reqSaleTicket.getDate());
        saleTicket.setTotal(reqSaleTicket.getTotal());
        saleTicket.setStatus(StatusEnum.PREPARING);
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
    
    public SaleTicket getSaleTicketById(long id){
        Optional<SaleTicket> saleTicket = this.saleTicketRepository.findById(id);
        if(saleTicket.isPresent()){
            return saleTicket.get();
        }
        return null;
    }

    public List<SaleTicket> getSaleTicketByUser(String email){
        User user = this.userService.handleGetUserByUsername(email);
        List<SaleTicket> listSaleTickets = this.saleTicketRepository.findByUser(user);
        return listSaleTickets;
    }

    public ResSaleTicketDetail getDetailOfSaleTicket(long id){
        SaleTicket saleTicket = this.getSaleTicketById(id);
        if(saleTicket != null){
            ResSaleTicketDetail resSaleTicketDetail = new ResSaleTicketDetail();
            List<SaleTicketDetail> lists = this.saleTicketDetailRepository.findBySaleTicket(saleTicket);
            List<ProductInTicket> listProduct = new ArrayList<>();
            for(SaleTicketDetail saleTicketDetail : lists){
                ResSaleTicketDetail.ProductInTicket product = new ProductInTicket(saleTicketDetail.getProduct(), saleTicketDetail.getQuantity());
                listProduct.add(product);
            }
            resSaleTicketDetail.setListProducts(listProduct);
            return resSaleTicketDetail;
        }
        return null;
    }

    public SaleTicket confirmComplete(long id){
        SaleTicket saleTicket = this.getSaleTicketById(id);
        saleTicket.setStatus(StatusEnum.COMPLETED);
        List<SaleTicketDetail> lSaleTicketDetails = this.saleTicketDetailRepository.findBySaleTicket(saleTicket);
        for(SaleTicketDetail saleTicketDetail : lSaleTicketDetails){
            Product product = this.productService.getProductById(saleTicketDetail.getProduct().getId());
            product.setQuantity(product.getQuantity() - saleTicketDetail.getQuantity());
            product = this.productRepository.save(product);
        }
        return this.saleTicketRepository.save(saleTicket);
    }

    public SaleTicket confirmDelivery(long id){
        SaleTicket saleTicket = this.getSaleTicketById(id);
        saleTicket.setStatus(StatusEnum.DELIVERING);
        return this.saleTicketRepository.save(saleTicket);
    }

    public ResultPaginationDTO fetchAllSaleTickets(Specification<SaleTicket> spec, Pageable pageable) {
        Page<SaleTicket> page = this.saleTicketRepository.findAll(spec, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(page.getNumber() + 1);
        meta.setPageSize(page.getSize());
        meta.setPages(page.getTotalPages());
        meta.setTotal(page.getTotalElements());
        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(page.getContent());
        return resultPaginationDTO;
    }

    public void deleteSaleTicket(long id){
        SaleTicket saleTicket = this.getSaleTicketById(id);
        List<SaleTicketDetail> list = this.saleTicketDetailRepository.findBySaleTicket(saleTicket);
        for(SaleTicketDetail saleTicketDetail : list){
            this.saleTicketDetailRepository.delete(saleTicketDetail);
        }
        this.saleTicketRepository.delete(saleTicket);
    }
}
