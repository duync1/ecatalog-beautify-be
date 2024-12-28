package Beauty_ECatalog.Beauty_ECatalog.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Beauty_ECatalog.Beauty_ECatalog.domain.Inventory;

import Beauty_ECatalog.Beauty_ECatalog.service.InventoryService;

@RestController
public class InventoryController {
     private final InventoryService inventoryService;
     public InventoryController(InventoryService inventoryService){
         this.inventoryService = inventoryService;
     }

     @PostMapping("/Inventory/Save")
    public ResponseEntity<Inventory> saveInventory(@RequestParam("month") int month, @RequestParam("year") int year) {
        Inventory inventory = inventoryService.saveInventoryForMonthAndYear(month, year);
        return ResponseEntity.ok(inventory);
    }

    @GetMapping("/Inventory")
    public ResponseEntity<Inventory> getInventory(@RequestParam("month") int month, @RequestParam("year") int year) {
        Inventory inventory = inventoryService.getInventoryForMonthAndYear(month, year);
        return ResponseEntity.ok(inventory);
    }
}
