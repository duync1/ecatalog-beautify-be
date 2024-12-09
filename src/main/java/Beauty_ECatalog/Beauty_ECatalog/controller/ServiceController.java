package Beauty_ECatalog.Beauty_ECatalog.controller;

import java.io.IOException;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.turkraft.springfilter.boot.Filter;

import Beauty_ECatalog.Beauty_ECatalog.domain.Servicee;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.ServiceService;
import Beauty_ECatalog.Beauty_ECatalog.util.error.IdInvalidException;

@RestController
public class ServiceController {
    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService){
        this.serviceService = serviceService;
    }

    @GetMapping("/Service/{id}")
    public ResponseEntity<Servicee> getService(@PathVariable("id") long id) throws IdInvalidException{
        Servicee servicee = this.serviceService.getService(id);
        if(servicee == null){
            throw new IdInvalidException("Service not found");
        }
        return ResponseEntity.ok().body(servicee);
    }

    @PostMapping("/Service")
    public ResponseEntity<Servicee> createService(@RequestParam("name") String name, @RequestParam("price") int price, 
        @RequestParam("shortDesc") String shortDesc, @RequestParam("detailDesc") String detailDesc, @RequestParam("image") MultipartFile img
    ) throws IOException, IdInvalidException{
        if(this.serviceService.checkExistService(name)){
            throw new IdInvalidException("Service da ton tai");
        }
        Servicee servicee = new Servicee();
        servicee.setName(name);
        servicee.setPrice(price);
        servicee.setShortDescription(shortDesc);
        servicee.setDetailDescription(detailDesc);
        return ResponseEntity.ok().body(this.serviceService.createService(servicee, img));
    }

    @GetMapping("/Service")
    public ResponseEntity<ResultPaginationDTO> getAllServices(@Filter Specification<Servicee> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.serviceService.fetchAllServices(spec, pageable));
    }

     
}
