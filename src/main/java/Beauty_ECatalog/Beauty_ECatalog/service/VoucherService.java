package Beauty_ECatalog.Beauty_ECatalog.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import Beauty_ECatalog.Beauty_ECatalog.domain.Voucher;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.repository.VoucherRepository;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    public VoucherService(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }

    public Voucher getDiscountById(long id){
        Optional<Voucher> discount = this.voucherRepository.findById(id);
        if(discount.isPresent()){
            return discount.get();
        }
        return null;
    }

    public Voucher createDiscount(Voucher discount){
        boolean isExistByName = this.voucherRepository.existsByName(discount.getName());
        if(isExistByName) return null;
        discount.setActive(true);
        return this.voucherRepository.save(discount);
    }

    public Voucher updateVoucher(Voucher voucher){
        boolean isExistByName = this.voucherRepository.existsByName(voucher.getName());
        if(isExistByName) return null;
        Voucher dbVoucher = this.getDiscountById(voucher.getId());
        dbVoucher.setDiscountValue(voucher.getDiscountValue());
        dbVoucher.setName(voucher.getName());
        dbVoucher.setPercentage(voucher.isPercentage());
        return this.voucherRepository.save(dbVoucher);
    }

    public void deleteVoucher(long id){
        Voucher voucher = this.getDiscountById(id);
        voucher.setActive(false);
        this.voucherRepository.save(voucher);
    }

    public void restoreVoucher(long id){
        Voucher voucher = this.getDiscountById(id);
        voucher.setActive(true);
        this.voucherRepository.save(voucher);
    }

    public ResultPaginationDTO fetchAllVouchers(Specification<Voucher> spec, Pageable pageable) {
        Page<Voucher> page = this.voucherRepository.findAll(spec, pageable);
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
}
