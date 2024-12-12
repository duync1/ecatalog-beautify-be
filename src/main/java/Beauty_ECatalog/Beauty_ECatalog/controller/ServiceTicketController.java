package Beauty_ECatalog.Beauty_ECatalog.controller;

import java.time.Instant;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import Beauty_ECatalog.Beauty_ECatalog.domain.ServiceTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResServiceTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.ServiceTicketService;
import Beauty_ECatalog.Beauty_ECatalog.util.error.IdInvalidException;

@RestController
public class ServiceTicketController {
    private final ServiceTicketService serviceTicketService;
    public ServiceTicketController(ServiceTicketService serviceTicketService){
        this.serviceTicketService = serviceTicketService;
    }

    @GetMapping("/ServiceTickets")
    public ResponseEntity<ResultPaginationDTO> getAllServiceTickets(@Filter Specification<ServiceTicket> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.serviceTicketService.fetchAllServiceTickets(spec, pageable));
    }

    @PostMapping("/ServiceTickets/Client")
    public ResponseEntity<ServiceTicket> bookingService(@RequestParam("cusId") long cusId, @RequestParam("cusName") String cusName, @RequestParam("cusPhone") String cusPhone, @RequestParam("date") Instant date,@RequestParam("serviceName") String serviceName, @RequestParam("totalPrice") int totalPrice){
        ServiceTicket serviceTicket = new ServiceTicket();
        serviceTicket.setDate(date);
        serviceTicket.setTotal(totalPrice);
        ServiceTicket curServiceTicket = this.serviceTicketService.createServiceTicketClient(serviceTicket, cusName, cusPhone, cusId, serviceName);
        return ResponseEntity.ok().body(curServiceTicket);
    }

    // @PostMapping("/ServiceTickets/Admin")
    // public ResponseEntity<ServiceTicket> bookingServiceAdmin(@RequestParam("cusName") String cusName, @RequestParam("cusPhone") String cusPhone, @RequestParam("date") Instant date, @RequestParam("serviceName") List<String> serviceName, @RequestParam("totalPrice") int totalPrice){

    // }

    @GetMapping("/ServiceTickets/Detail/{id}")
    public ResponseEntity<ResServiceTicketDetail> getServiceTicketDetail(@PathVariable("id") long id){
        ServiceTicket serviceTicket = this.serviceTicketService.getServiceTicket(id);
        return ResponseEntity.ok().body(this.serviceTicketService.getDetailOfSericeTicket(serviceTicket));
    }

    @PostMapping("/ServiceTickets/confirmServiceTicket")
    public ResponseEntity<ServiceTicket> confirmServiceTicket(@RequestParam("id") long id) throws IdInvalidException{
        ServiceTicket serviceTicket = this.serviceTicketService.getServiceTicket(id);
        if(serviceTicket == null){
            throw new IdInvalidException("Service Ticket khong ton tai");
        }
        return ResponseEntity.ok().body(this.serviceTicketService.confirmServiceTicket(serviceTicket));
    }

    @PostMapping("/ServiceTickets/confirmCheckout")
    public ResponseEntity<ServiceTicket> confirmCheckout(@RequestParam("id") long id) throws IdInvalidException{
        ServiceTicket serviceTicket = this.serviceTicketService.getServiceTicket(id);
        if(serviceTicket == null){
            throw new IdInvalidException("Service Ticket khong ton tai");
        }
        return ResponseEntity.ok().body(this.serviceTicketService.confirmCheckout(serviceTicket));
    }
}
