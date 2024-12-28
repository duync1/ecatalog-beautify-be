package Beauty_ECatalog.Beauty_ECatalog.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.ImportTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.ImportTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.Product;

import Beauty_ECatalog.Beauty_ECatalog.domain.Supplier;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqImportTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResImportTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResImportTicketDetail.ProductInTicket;
import Beauty_ECatalog.Beauty_ECatalog.repository.ImportTicketRepository;

@Service
public class ImportTicketService {
    private final ImportTicketRepository importTicketRepository;
    private final SupplierService supplierService;
    private final ProductService productService;
    private final ImportTicketDetailService importTicketDetailService;
    public ImportTicketService(ImportTicketRepository importTicketRepository, SupplierService supplierService, ProductService productService, ImportTicketDetailService importTicketDetailService){
        this.importTicketRepository = importTicketRepository;
        this.supplierService = supplierService;
        this.productService = productService;
        this.importTicketDetailService = importTicketDetailService;
    }

    public ImportTicket createImportTicket(ReqImportTicket reqImportTicket){
        ImportTicket importTicket = new ImportTicket();
        Supplier currentSupplier = this.supplierService.findByName(reqImportTicket.getSupplierName());
        importTicket.setSupplier(currentSupplier);
        importTicket.setDate(reqImportTicket.getDate());
        importTicket.setTotal(reqImportTicket.getTotalPrice());
        importTicket.setStatus(false);
        ImportTicket saveImportTicket = this.importTicketRepository.save(importTicket);
        for(ReqImportTicket.ProductInfor product : reqImportTicket.getListProducts()){
            Product currentProduct = this.productService.getProductByName(product.getProductName());
            ImportTicketDetail importTicketDetail = new ImportTicketDetail();
            importTicketDetail.setProduct(currentProduct);
            importTicketDetail.setQuantity(product.getQuantity());
            importTicketDetail.setImportPrice(product.getImportPrice());
            importTicketDetail.setImportTicket(saveImportTicket);
            importTicketDetail = this.importTicketDetailService.createImportTicketDetail(importTicketDetail);
        }
        return saveImportTicket;
    }

    public ResultPaginationDTO fetchAllImportTickets(Specification<ImportTicket> spec, Pageable pageable) {
        Page<ImportTicket> page = this.importTicketRepository.findAll(spec, pageable);
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

    public ImportTicket getImportTicket(long id){
        Optional<ImportTicket> importTicket = this.importTicketRepository.findById(id);
        if(importTicket.isPresent()){
            return importTicket.get();
        }
        return null;
    }

    public ImportTicket completeTicket(ImportTicket importTicket){
        ImportTicket currentImportTicket = this.getImportTicket(importTicket.getId());
        currentImportTicket.setStatus(true);
        List<ImportTicketDetail> lists = this.importTicketDetailService.getDetailByImportTicket(currentImportTicket);
        for(ImportTicketDetail ticket : lists){
            Product product = ticket.getProduct();
            product.setQuantity(product.getQuantity() + ticket.getQuantity());
            product = this.productService.addQuanityFromImport(product);
        }
        return this.importTicketRepository.save(currentImportTicket);
    }

    public void handleDeleteImportTicket(long id){
        ImportTicket importTicket = this.getImportTicket(id);
        this.importTicketDetailService.deleteImportTicketDetail(importTicket.getImport_ticket_details());
        this.importTicketRepository.delete(importTicket);
    }

    public ResImportTicketDetail getDetailOfImportTicket(long id){
        ImportTicket importTicket = this.getImportTicket(id);
        ResImportTicketDetail resImportTicketDetail = new ResImportTicketDetail();
        String supplierName = importTicket.getSupplier().getName();
        resImportTicketDetail.setSupplier(supplierName);
        resImportTicketDetail.setDate(importTicket.getDate());
        resImportTicketDetail.setTotal(importTicket.getTotal());
        resImportTicketDetail.setStatus(importTicket.isStatus());
        List<ProductInTicket> lists = new ArrayList<>();
        List<ImportTicketDetail> listsDetails = this.importTicketDetailService.getDetailByImportTicket(importTicket);
        for(ImportTicketDetail importTicketDetail : listsDetails){
            ResImportTicketDetail.ProductInTicket product = new ProductInTicket(importTicketDetail.getProduct(), importTicketDetail.getQuantity());
            lists.add(product);
        }
        resImportTicketDetail.setListProducts(lists);
        return resImportTicketDetail;
    }
}
