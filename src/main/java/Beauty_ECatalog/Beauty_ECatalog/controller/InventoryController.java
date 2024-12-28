package Beauty_ECatalog.Beauty_ECatalog.controller;



import org.springframework.http.ResponseEntity;

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
        Inventory inventory = inventoryService.saveOrUpdateInventory(month, year);
        return ResponseEntity.ok(inventory);
    }
}
