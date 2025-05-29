package Beauty_ECatalog.Beauty_ECatalog.controller;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicket;

import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqSaleTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqSaleTicketAdmin;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResSaleTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.SaleTicketService;

@RestController
public class SaleTicketController {
    private final SaleTicketService saleTicketService;

    public SaleTicketController(SaleTicketService saleTicketService){
        this.saleTicketService = saleTicketService;
    }

    @PostMapping("/SaleTickets/Client")
    public ResponseEntity<SaleTicket> createSaleTicket(@RequestBody ReqSaleTicket reqSaleTicket) {
        return ResponseEntity.ok().body(this.saleTicketService.createSaleTicket(reqSaleTicket));
    }

    @GetMapping("/SaleTickets/Client/GetAllSaleTickets")
    public ResponseEntity<List<SaleTicket>> getAllSaleTickets(@RequestParam("email") String email){
        return ResponseEntity.ok().body(this.saleTicketService.getSaleTicketByUser(email));
    }

    @GetMapping("/SaleTickets/Clients/GetDetail/{id}")
    public ResponseEntity<ResSaleTicketDetail> getDetailSaleTicket(@PathVariable("id") long id){
        return ResponseEntity.ok().body(this.saleTicketService.getDetailOfSaleTicket(id));
    }

    @PostMapping("/SaleTickets/ConfirmComplete")
    public ResponseEntity<SaleTicket> confirmComplete(@RequestParam("id") long id){
        return ResponseEntity.ok().body(this.saleTicketService.confirmComplete(id));
    }

    @PostMapping("/SaleTickets/ConfirmDelivery")
    public ResponseEntity<SaleTicket> confirmDelivery(@RequestParam("id") long id){
        return ResponseEntity.ok().body(this.saleTicketService.confirmDelivery(id));
    }

    @GetMapping("/SaleTickets")
    public ResponseEntity<ResultPaginationDTO> getAllSaleTickets(@Filter Specification<SaleTicket> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.saleTicketService.fetchAllSaleTickets(spec, pageable));
    }

    @DeleteMapping("/SaleTickets/{id}")
    public ResponseEntity<Void> deleteSaleTicket(@PathVariable("id") long id){
        this.saleTicketService.deleteSaleTicket(id);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/SaleTickets/CreateTicketAdmin")
    public ResponseEntity<SaleTicket> createTicketAdmin(@RequestBody ReqSaleTicketAdmin reqSaleTicketAdmin){
        return ResponseEntity.ok().body(this.saleTicketService.createSaleAdmin(reqSaleTicketAdmin));
    }
    
}
