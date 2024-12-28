package Beauty_ECatalog.Beauty_ECatalog.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;


import org.springframework.stereotype.Service;

import Beauty_ECatalog.Beauty_ECatalog.domain.Inventory;
import Beauty_ECatalog.Beauty_ECatalog.domain.InventoryDetail;
import Beauty_ECatalog.Beauty_ECatalog.domain.Product;
import Beauty_ECatalog.Beauty_ECatalog.repository.ImportTicketDetailRepository;
import Beauty_ECatalog.Beauty_ECatalog.repository.InventoryRepository;
import Beauty_ECatalog.Beauty_ECatalog.repository.ProductRepository;
import Beauty_ECatalog.Beauty_ECatalog.repository.SaleTicketDetailRepository;

@Service
public class InventoryService {

    private final ProductRepository productRepository;
    private final SaleTicketDetailRepository saleTicketDetailRepository;
    private final ImportTicketDetailRepository importTicketDetailRepository;
    private final InventoryRepository inventoryRepository;
    public InventoryService(ProductRepository productRepository,
                            SaleTicketDetailRepository saleTicketDetailRepository,
                            ImportTicketDetailRepository importTicketDetailRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.saleTicketDetailRepository = saleTicketDetailRepository;
        this.importTicketDetailRepository = importTicketDetailRepository;
        this.inventoryRepository = inventoryRepository;
    }

    
    public Inventory saveOrUpdateInventory(int month, int year) {
        // Kiểm tra xem Inventory đã tồn tại chưa
        Optional<Inventory> existingInventory = inventoryRepository.findByMonthAndYear(month, year);
        
        Inventory inventory = existingInventory.orElseGet(() -> {
            Inventory newInventory = new Inventory();
            newInventory.setMonth(month);
            newInventory.setYear(year);
            newInventory.setDetails(new ArrayList<>());
            return newInventory;
        });
    
        // Tạo khoảng thời gian (range) cho tháng và năm được yêu cầu
        LocalDate startLocalDate = LocalDate.of(year, month, 1);
        LocalDate endLocalDate = startLocalDate.with(TemporalAdjusters.lastDayOfMonth());
        Instant startDate = startLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endDate = endLocalDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();
    
        // Lấy danh sách tất cả các sản phẩm
        List<Product> products = productRepository.findAll();
    
        // Cập nhật InventoryDetail cho từng sản phẩm
        List<InventoryDetail> details = new ArrayList<>();
        for (Product product : products) {
            int beginningInventory = product.getQuantity();
    
            Integer totalImported = importTicketDetailRepository.findTotalImportedByProductAndDateRange(
                    product.getId(), startDate, endDate);
            totalImported = (totalImported != null) ? totalImported : 0;
    
            Integer totalSold = saleTicketDetailRepository.findTotalSoldByProductAndDateRange(
                    product.getId(), startDate, endDate);
            totalSold = (totalSold != null) ? totalSold : 0;
    
            int endingInventory = beginningInventory + totalImported - totalSold;
    
            // Tạo hoặc cập nhật InventoryDetail
            InventoryDetail detailInventory = new InventoryDetail();
            detailInventory.setProductName(product.getName());
            detailInventory.setBeginningInventory(beginningInventory);
            detailInventory.setTotalImported(totalImported);
            detailInventory.setTotalSold(totalSold);
            detailInventory.setEndingInventory(endingInventory);
            detailInventory.setInventory(inventory);
    
            details.add(detailInventory);
        }
    
        // Cập nhật Inventory với các InventoryDetail mới
        inventory.setDetails(details);
    
        // Lưu Inventory
        return inventoryRepository.save(inventory);
    }
    
}