package Beauty_ECatalog.Beauty_ECatalog.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


import Beauty_ECatalog.Beauty_ECatalog.domain.Supplier;
import Beauty_ECatalog.Beauty_ECatalog.domain.response.ResultPaginationDTO;
import Beauty_ECatalog.Beauty_ECatalog.repository.SupplierRepository;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    public SupplierService(SupplierRepository supplierRepository){
        this.supplierRepository = supplierRepository;
    }

    public Supplier findById(Long id){
        Optional<Supplier> supplier = this.supplierRepository.findById(id);
        if(supplier.isPresent()){
            return supplier.get();
        }
        return null;
    }

    public Supplier findByName(String name){
        Supplier supplier = this.supplierRepository.findByName(name);
        if(supplier != null){
            return supplier;
        }
        return null;
    }

    public Supplier createSupplier(Supplier supplier){
        return this.supplierRepository.save(supplier);
    }

    public boolean checkExistsByName(String name){
        return this.supplierRepository.existsByName(name);
    }

    public ResultPaginationDTO fetchAllSuppliers(Specification<Supplier> spec, Pageable pageable) {
        Page<Supplier> page = this.supplierRepository.findAll(spec, pageable);
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

    public Supplier handleUpdateSupplier(Supplier supplier){
        Supplier currentSupplier = this.findById(supplier.getId());
        if(currentSupplier != null){
            currentSupplier.setName(supplier.getName());
            currentSupplier.setPhone(supplier.getPhone());
            currentSupplier.setEmail(supplier.getEmail());
            currentSupplier.setAddress(supplier.getAddress());
            return this.supplierRepository.save(currentSupplier);
        }
        return null;
    }

    public void handleDeleteSupplier(long id){
        Supplier supplier = this.findById(id);
        supplier.setDeleted(true);
        this.supplierRepository.save(supplier);
    }

    public void handleRestoreSupplier(long id){
        Supplier supplier = this.findById(id);
        supplier.setDeleted(false);
        this.supplierRepository.save(supplier);
    }
}
