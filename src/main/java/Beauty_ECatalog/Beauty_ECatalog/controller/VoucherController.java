package Beauty_ECatalog.Beauty_ECatalog.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import Beauty_ECatalog.Beauty_ECatalog.domain.Voucher;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.service.VoucherService;
import Beauty_ECatalog.Beauty_ECatalog.util.error.IdInvalidException;


@RestController
public class VoucherController {
    private final VoucherService voucherService;
    public VoucherController(VoucherService voucherService){
        this.voucherService = voucherService;
    }

    @GetMapping("/Voucher/{id}")
    public ResponseEntity<Voucher> getDiscountById(@PathVariable("id") long id) throws IdInvalidException{
        Voucher discount = this.voucherService.getDiscountById(id);
        if(discount == null){
            throw new IdInvalidException("Discount nay khong ton tai");
        }
        return ResponseEntity.ok().body(discount);
    }

    @PostMapping("/Voucher")
    public ResponseEntity<Voucher> createDiscount(@RequestBody Voucher voucher) throws IdInvalidException{
        Voucher newVoucher = this.voucherService.createDiscount(voucher);
        if(newVoucher == null){
            throw new IdInvalidException("Ten voucher da ton tai");
        }
        return ResponseEntity.ok().body(newVoucher);
    }

    @PutMapping("/Voucher")
    public ResponseEntity<Voucher> updateVoucher(@RequestBody Voucher voucher) throws IdInvalidException{
        Voucher updateVoucher = this.voucherService.updateVoucher(voucher);
        if(updateVoucher == null){
            throw new IdInvalidException("Ten voucher da ton tai");
        }
        return ResponseEntity.ok().body(updateVoucher);
    }

    @DeleteMapping("/Voucher/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable("id") long id){
        this.voucherService.deleteVoucher(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/Voucher/Back/{id}")
    public ResponseEntity<Void> restoreVoucher(@PathVariable("id") long id){
        this.voucherService.restoreVoucher(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/Voucher")
    public ResponseEntity<ResultPaginationDTO> getAllVouchers(@Filter Specification<Voucher> spec, Pageable pageable){
        return ResponseEntity.ok().body(this.voucherService.fetchAllVouchers(spec, pageable));
    }
}
