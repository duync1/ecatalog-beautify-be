package Beauty_ECatalog.Beauty_ECatalog.controller;


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

import Beauty_ECatalog.Beauty_ECatalog.domain.ImportTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.request.ReqImportTicket;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResImportTicketDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.ImportTicketService;
import Beauty_ECatalog.Beauty_ECatalog.util.error.IdInvalidException;


@RestController
public class ImportTicketController {
    private final ImportTicketService importTicketService;
    public ImportTicketController(ImportTicketService importTicketService){
        this.importTicketService = importTicketService;
    }

    @PostMapping("/ImportTicket/Create")
    public ResponseEntity<ImportTicket> createImportTicket(@RequestBody ReqImportTicket reqImportTicket){
        ImportTicket saveImportTicket = this.importTicketService.createImportTicket(reqImportTicket);
        return ResponseEntity.ok().body(saveImportTicket);
    }

    @GetMapping("/ImportTickets")
    public ResponseEntity<ResultPaginationDTO> getAllImportTicket(@Filter Specification<ImportTicket> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.importTicketService.fetchAllImportTickets(spec, pageable));
    }

    @PostMapping("/ImportTicket/CompleteTicket")
    public ResponseEntity<ImportTicket> completeTicket(@RequestParam("id") long id) throws IdInvalidException{
        ImportTicket importTicket = this.importTicketService.getImportTicket(id);
        if(importTicket == null){
            throw new IdInvalidException("Import ticket khong ton tai");
        }
        return ResponseEntity.ok().body(this.importTicketService.completeTicket(importTicket));
    }

    @DeleteMapping("/ImportTicket/{id}")
    public ResponseEntity<Void> deleteImportTicket(@PathVariable("id") long id){
        this.importTicketService.handleDeleteImportTicket(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/ImportTicket/Detail/{id}")
    public ResponseEntity<ResImportTicketDetail> getImportTicketDetail(@PathVariable("id") long id){
        return ResponseEntity.ok().body(this.importTicketService.getDetailOfImportTicket(id));
    }
}
