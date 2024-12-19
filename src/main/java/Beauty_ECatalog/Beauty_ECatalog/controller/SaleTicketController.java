package Beauty_ECatalog.Beauty_ECatalog.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Beauty_ECatalog.Beauty_ECatalog.domain.SaleTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqProduct;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqSaleTicket;
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
}
