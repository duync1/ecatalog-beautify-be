package Beauty_ECatalog.Beauty_ECatalog.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;


import Beauty_ECatalog.Beauty_ECatalog.domain.Supplier;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.SupplierService;
import Beauty_ECatalog.Beauty_ECatalog.util.error.IdInvalidException;

@RestController
public class SupplierController {
    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }

    @PostMapping("/Supplier")
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) throws IdInvalidException{
        boolean check = this.supplierService.checkExistsByName(supplier.getName());
        if(check){
            throw new IdInvalidException("Ten supplier da ton tai");
        }
        return ResponseEntity.ok().body(this.supplierService.createSupplier(supplier));
    }

    @GetMapping("/Suppliers")
    public ResponseEntity<ResultPaginationDTO> getAllSuppliers(@Filter Specification<Supplier> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.supplierService.fetchAllSuppliers(spec, pageable));
    }
}
